package com.example.uilab;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.stage.FileChooser;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author saturday
 * @version 1.0.0
 * date: 2024/12/31 11:49
 */
public class MainController {

    @FXML
    public Button sendRequest;
    @FXML
    public ImageView imagePane;
    @FXML
    public TextArea responseText;
    @FXML
    public AnchorPane drawArea;
    @FXML
    private TextField filePath;
    private static int shiftParam = 1000;

    @FXML
    public void chooseFile(ActionEvent actionEvent) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
        String absolutePath = fileChooser.showOpenDialog(null).getAbsolutePath();
        filePath.setText(absolutePath);

        if (!absolutePath.isEmpty()) {
            // 渲染文件
            imagePane.setImage(new ImageView(absolutePath).getImage());
        }
    }

    @FXML
    public void sendRequest(ActionEvent actionEvent) {
        if (filePath.getText().isEmpty()) {
            responseText.setText("请选择图片");
            return;
        }
        sendRequest.setDisable(true);
        OkHttpClient client = new OkHttpClient.Builder()
                .callTimeout(60000, TimeUnit.MILLISECONDS)
                .readTimeout(60000, TimeUnit.MILLISECONDS)
                .build()
                ;
        // 创建文件请求
        File imageFile = new File(filePath.getText());
        MediaType mediaType = MediaType.parse("image/jpeg");
        RequestBody requestBody = RequestBody.create(imageFile, mediaType);
        // 设置file的key
        MultipartBody imageBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", imageFile.getName(), requestBody)
                .build();
        Headers headers = new Headers.Builder()
            .add("appToken", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1aWQiOiIxNzA0ODMzOTc1Nzk0NTMyMzUyIiwibmFtZSI6IiIsImV4cCI6MjA0MjE2MTEzNSwiaWF0IjoxNzMxMTIxMTM1fQ.oNUiFhR4hhmKj2CYPIDoDoEMFphr4e74-a9kq1IiUjU")
            .add("appIp", "127.0.0.1")
            .add("appType", "uniqueme")
            .add("appVersion", "1.0.0")
            .add("userMark", "saturday")
            .add("languageCode", "zh-CN")
            .add("sysType", "2")
            .build();
        // 上传文件
        client.newCall(new Request.Builder()
                .url("http://192.168.31.15:8180/search/ocr/bwcode")
                        .headers(headers)
                .post(imageBody)
                .build())
                .enqueue(new Callback() {

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        // 解析结果，绘制位置
                        assert response.body() != null;
                        JSONObject resp = JSONUtil.parseObj(response.body().string());
                        // 设置响应信息
                        responseText.clear();
                        responseText.setText(JSONUtil.toJsonPrettyStr(resp));
                        // 获取图片的实际大小
                        double width = imagePane.getImage().getWidth();
                        double height = imagePane.getImage().getHeight();
                        System.out.println("width:" + width + ",height:" + height);
                        drawArea.setPrefWidth(width);
                        drawArea.setPrefHeight(height);
                        System.out.println("drawArea prefWidth:" + drawArea.getPrefWidth() + ",prefHeight:" + drawArea.getPrefHeight());
                        // 绘制矩形
                        JSONArray objs = resp.getJSONArray("data");
                        Platform.runLater(()-> {
                            drawArea.getChildren().removeIf(node -> (node instanceof Rectangle)||(node instanceof Text));
                        });
                        try {
                            for (Object obj : objs) {
                                JSONObject objJson = (JSONObject) obj;
                                JSONObject objectLocation = objJson.getJSONObject("objectLocation");
                                double x = objectLocation.getLong("x1", 0L).doubleValue();
                                double y = objectLocation.getLong("y1", 0L).doubleValue();
                                double x2 = objectLocation.getLong("x2", 0L).doubleValue();
                                double y2 = objectLocation.getLong("y2", 0L).doubleValue();
                                x = x * width / shiftParam;
                                y = y * height / shiftParam;
                                x2 = x2 * width / shiftParam;
                                y2 = y2 * height / shiftParam;
                                System.out.println("x:" + x + ",y:" + y + ",x2:" + x2 + ",y2:" + y2);

                                String label = objJson.getStr("objectNameCn");
                                // 绘制位置
                                // 在imagePane图片上面绘制矩形
                                double finalX = x;
                                double finalY = y;
                                double finalX1 = x2;
                                double finalY1 = y2;
                                Platform.runLater(() -> {
                                    Rectangle rect = new Rectangle(finalX, finalY, finalX1 - finalX, finalY1 - finalY);
                                    System.out.println("rect:" + rect.getX() + "," + rect.getY() + "," + rect.getWidth() + "," + rect.getHeight());
                                    rect.setFill(javafx.scene.paint.Color.TRANSPARENT);
                                    rect.setStroke(javafx.scene.paint.Color.RED);
                                    drawArea.getChildren().add(rect);
                                    Text text = new Text(finalX, finalY-4, label);
                                    text.setBoundsType(TextBoundsType.VISUAL);
                                    text.setFill(Color.GREEN);
                                    drawArea.getChildren().add(text);
                                });

                            }
                            System.out.println("================DRAW END==================");

                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        } finally {
                            Platform.runLater(()->sendRequest.setDisable(false));
                        }

                    }

                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        System.err.println(e.getMessage());
                        Platform.runLater(()->sendRequest.setDisable(false));
                    }
                });
    }
}

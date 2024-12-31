## javafx实验项目

这便是专门用来做各种各样的UI工具的，这个是需要认真对待的。

## 调试

运行的时候，直接执行命令： `mvn clean javafx:run`

调试的时候，选择的是创建application，对应的选择main方法，然后需要添加运行参数： 

```shell
--add-modules javafx.controls,javafx.fxml
```
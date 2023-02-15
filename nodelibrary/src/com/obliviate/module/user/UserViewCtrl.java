package com.obliviate.module.user;

import com.gn.App;
import com.obliviate.bean.Constant;
import com.obliviate.bean.User;
import com.obliviate.global.util.Alerts;
import com.obliviate.service.UserService;
import com.obliviate.service.impl.UserServiceImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * 用户管理
 * @author admin
 */
public class UserViewCtrl implements Initializable {

    @FXML
    private TableView<User> userTableView;
    @FXML
    private TableColumn<User, String> c1;
    @FXML
    private TableColumn<User, String> c2;
    @FXML
    private TableColumn<User, String> c3;
    @FXML
    private TableColumn<User, String> c4;

    ObservableList<User> users = FXCollections.observableArrayList();

    private UserService userService = new UserServiceImpl();

    /**
     * 查询所有用户信息
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //调用service层查询用户数据
        List<User> userList = userService.select();
        users.addAll(userList);
        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        c2.setCellValueFactory(new PropertyValueFactory<>("name"));
        c3.setCellValueFactory(new PropertyValueFactory<>("money"));
        c4.setCellValueFactory(new PropertyValueFactory<>("status"));
        userTableView.setItems(users);

    }

    /**
     * 添加用户
     */
    @FXML
    private void userAddView() {
        try {
            initStage(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改用户
     */
    @FXML
    private void userEditView() {
        try {
            User user = this.userTableView.getSelectionModel().getSelectedItem();
            if (user == null){
                Alerts.warning("未选择","请先选择要修改的数据");
                return;
            }
            initStage(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除用户
     */
    @FXML
    private void deleteUser() {
        try {
            User user = this.userTableView.getSelectionModel().getSelectedItem();
            if (user == null){
                Alerts.warning("未选择","请先选择要删除的数据");
                return;
            }
            userService.delete(user.getId());
            this.users.remove(user);
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败","操作失败");
        }
    }

    /**
     * 冻结用户
     */
    @FXML
    private void frozen() {
        User user = this.userTableView.getSelectionModel().getSelectedItem();
        if (user == null){
            Alerts.warning("未选择","请先选择要修改的数据");
            return;
        }
        userService.frozen(user.getId());
        user.setStatus(Constant.USER_FROZEN);
        userTableView.refresh();
    }

    @FXML
    private void chargeView() {
        try {
            User user = this.userTableView.getSelectionModel().getSelectedItem();
            if (user == null){
                Alerts.warning("未选择","请先选择要充值的数据");
                return;
            }
            initChargeStage(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        初始化充值stage
     */
    private void initChargeStage(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/obliviate/module/user/UserChargeView.fxml"));
        StackPane target = (StackPane) loader.load();
        Scene scene = new Scene(target);

        Stage stage = new Stage();//创建舞台；
        UserChargeViewCtrl controller = (UserChargeViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setUser(user);
        controller.setUserTableView(userTableView);
        stage.setHeight(500);
        stage.setWidth(400);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }

    /*
        初始化stage
     */
    private void initStage(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/obliviate/module/user/UserHandleView.fxml"));
        StackPane target = loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//创建舞台；
        UserHandleViewCtrl controller = loader.getController();
        controller.setStage(stage);
        controller.setUsers(users);
        controller.setUser(user);
        controller.setUserTableView(userTableView);
//        stage.setResizable(false);
        stage.setHeight(500);
        stage.setWidth(400);
        //设置窗口图标
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //将场景载入舞台；
        stage.show(); //显示窗口；
    }
}

package com.obliviate.module.lend;


import com.obliviate.bean.Constant;
import com.obliviate.bean.Lend;
import com.obliviate.global.util.Alerts;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class LendHandleViewCtrl {

    @FXML
    private TextField lendIdField;

    @FXML
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    @FXML
    private TextField authorField;

    @FXML
    private TextField publisherField;

    @FXML
    private ComboBox typeField;

    private Stage stage;

    private TableView<Lend> lendTableView;

    private ObservableList<Lend> lends;

    //修改的lend对象
    private Lend lend;

    /**
     * 添加或修改数据
     */
    @FXML
    private void addOrEditLend() {
        try {
            String id = lendIdField.getText();
            if ("".equals(id) || null == id) {
                //添加操作
                Lend lend = new Lend();
                populate(lend);
                lend.setStatus(Constant.STATUS_STORAGE);
                lends.add(lend);
            }else {
                //修改操作
                populate(this.lend);
                //刷新
                lendTableView.refresh();
            }

            stage.close();
            Alerts.success("成功", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            Alerts.error("失败","操作失败");
        }

    }

    private void populate(Lend lend) {
    }

    @FXML
    private void closeView() {
        stage.close();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public ObservableList<Lend> getLends() {
        return lends;
    }

    public void setLends(ObservableList<Lend> lends) {
        this.lends = lends;
    }

    public Lend getLend() {
        return lend;
    }

    public void setLend(Lend lend) {
        this.lend = lend;
        if (lend != null) {
            //填充修改界面的值
//            lendNameField.setText(lend.getLendName());
//            authorField.setText(lend.getAuthor());
//            isbnField.setText(lend.getIsbn());
//            lendIdField.setText(String.valueOf(lend.getId()));
//            publisherField.setText(lend.getPublisher());
//            typeField.setValue(lend.getType());
        }

    }

    public TableView<Lend> getLendTableView() {
        return lendTableView;
    }

    public void setLendTableView(TableView<Lend> lendTableView) {
        this.lendTableView = lendTableView;
    }
}

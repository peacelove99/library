package com.obliviate.module.lend;

import com.gn.App;
import com.obliviate.bean.Book;
import com.obliviate.bean.Constant;
import com.obliviate.bean.Lend;
import com.obliviate.bean.User;
import com.obliviate.global.util.Alerts;
import com.obliviate.service.LendService;
import com.obliviate.service.UserService;
import com.obliviate.service.impl.LendServiceImpl;
import com.obliviate.service.impl.UserServiceImpl;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * ͼ??????
 *
 * @author admin
 */
public class LendViewCtrl implements Initializable {

    @FXML
    private TableView<Lend> lendTableView;
    @FXML
    private TableColumn<Lend, String> c1;
    @FXML
    private TableColumn<Lend,String> c2;
    @FXML
    private TableColumn<Lend, String> c3;
    @FXML
    private TableColumn<Lend, String> c4;
    @FXML
    private TableColumn<Lend, String> c5;
    @FXML
    private TableColumn<Lend, String> c6;
    @FXML
    private TableColumn<Lend, String> c7;

    @FXML
    private TextField lendNameField;

    @FXML
    private TextField isbnField;

    ObservableList<Lend> lends = FXCollections.observableArrayList();

    private LendService lendService = new LendServiceImpl();
    private UserService userService = new UserServiceImpl();

    /**
     * չʾ???н?????Ϣ
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Lend> lendList = lendService.select(null);
        //?????鼮???ں???????
        lendList.forEach(d -> {
            LocalDate returnDate = d.getReturnDate();
            LocalDate now = LocalDate.now();
            //???????ڲ?
            Period period = Period.between(returnDate, now);

            User user = d.getUser();
            BigDecimal money = user.getMoney();//??ǰ????
            BigDecimal delay = BigDecimal.ZERO;//?ۿ?????
            if (period.getDays() >= 1) {
                /*
                    ????
                    ????????30?????ۿ?30Ԫ
                    ????û?г???30???򳬳?1????1Ԫ
                    ?жϵ?ǰ?û????????Ƿ?????0??????????С??0???????û?״̬?ĳɶ???
                 */
                //???????ɽ?
                if (period.getDays() >= 30) {
                    delay = new BigDecimal("30");
                }else {
                    delay = new BigDecimal(period.getDays());
                }
                //?ۿ?
                user.setMoney(money.subtract(delay));
                //???黹???ڸ?Ϊ????
                d.setReturnDate(now);

                //?ж??Ƿ???Ҫ?????û?
                if (BigDecimal.ZERO.compareTo(user.getMoney()) > 0) {
                    user.setStatus(Constant.USER_FROZEN);
                }

                d.setUser(user);
                lendService.update(d);
                userService.update(user);

            }

        });


        lends.addAll(lendList);

        c1.setCellValueFactory(new PropertyValueFactory<>("id"));
        //??ȡͼ??????
        c2.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getBookName())
        );
        c3.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getBook().getIsbn())
        );
        c4.setCellValueFactory((TableColumn.CellDataFeatures<Lend, String> p) ->
                new SimpleObjectProperty(p.getValue().getUser().getName())
        );
        c5.setCellValueFactory(new PropertyValueFactory<>("lendDate"));
        c6.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        c7.setCellValueFactory(new PropertyValueFactory<>("status"));
        lendTableView.setItems(lends);

    }


    /**
     * ??ѯ??????Ϣ
     */
    @FXML
    private void lendSelect(){
        String lendName = lendNameField.getText();
        String isbn = isbnField.getText();
        boolean lendFlag = "".equals(lendName);
        boolean isbnFlag = "".equals(isbn);
        ObservableList<Lend> result = lends;
        if (lendFlag && isbnFlag) { return; }

        lends = new ObservableListWrapper<Lend>(new ArrayList<Lend>(result));
        lendTableView.setItems(lends);
    }

    /**
     * ????
     */
    @FXML
    private void returnBook(){
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("δѡ??", "????ѡ??Ҫ?黹???鼮");
            return;
        }
        List<Lend> lendList = lendService.returnBook(lend);
        lends = new ObservableListWrapper<Lend>(new ArrayList<>(lendList));
        lendTableView.setItems(lends);
    }

    /**
     * ????
     */
    @FXML
    private void renew(){
        Lend lend = this.lendTableView.getSelectionModel().getSelectedItem();
        if (lend == null) {
            Alerts.warning("δѡ??", "????ѡ??Ҫ???????鼮");
            return;
        }
        lend.setReturnDate(LocalDate.now().plusDays(30));
    }


    /*
        ??ʼ??stage
     */
    private void initStage(Lend lend) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class.getResource("/com/obliviate/module/lend/LendHandleView.fxml"));
        StackPane target = (StackPane) loader.load();
        //Scene scene1 = App.getDecorator().getScene();
        Scene scene = new Scene(target);


        Stage stage = new Stage();//??????̨??
        LendHandleViewCtrl controller = (LendHandleViewCtrl)loader.getController();
        controller.setStage(stage);
        controller.setLends(lends);
        controller.setLend(lend);
        controller.setLendTableView(lendTableView);
//        stage.setResizable(false);
        stage.setHeight(800);
        stage.setWidth(700);
        //???ô???ͼ??
        stage.getIcons().add(new Image("icon.png"));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene); //????????????̨??
        stage.show(); //??ʾ???ڣ?
    }
}

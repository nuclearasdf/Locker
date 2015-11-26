package com.virus;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

/**
 * Created by 강희룡
 * 2015-11-26
 */
public class PurchaseController {
    @FXML
    private TextField txtDecryptCode;
    @FXML
    private Hyperlink lnkPurchaseCode;

    public Alert alert;

    private static final String CODE = "test code";


    /**
     * 하이퍼링크 이벤트 핸들러
     * "https://www.paypal.com?cmd=_pay-inv&id=INV2-YFHE-6XJF-AKME-XJBJ"로 연결되면 됨
     * @param event
     */
    public void handleLinkAction(ActionEvent event) {
        Main.viewWeb("https://www.paypal.com?cmd=_pay-inv&id=INV2-YFHE-6XJF-AKME-XJBJ");
    }


    /**
     * Submit 버튼 이벤트 핸들러
     * @param event
     */
    public void handleSubmitAction(ActionEvent event) {
        String code = txtDecryptCode.getText();

        if(code.trim().equals("")) {
            alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("복구 코드를 입력해야합니다");
            alert.setContentText("복구 코드를 입력하세요");

            alert.showAndWait();
        } else {
            if(code.equals(CODE)) {
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("축하합니다.");
                alert.setContentText("복구 코드가 확인되었습니다. 복호화를 시작합니다.");

                alert.showAndWait();

            } else {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fail");
                alert.setHeaderText("복구 실패");
                alert.setContentText("잘못된 복구 코드입니다.");

                alert.showAndWait();
            }
        }
    }
}

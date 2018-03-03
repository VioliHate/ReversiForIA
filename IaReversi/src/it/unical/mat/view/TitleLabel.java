package it.unical.mat.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

public class TitleLabel extends Label { // {{{
    final int padding = 30;
    private TitleLabel() {}
    public TitleLabel(int fontSize, boolean bold) {
        setFont(new Font(30));
        setMaxWidth(Double.MAX_VALUE);
        setAlignment(Pos.CENTER);
        setPadding(new Insets(30,0,30,0));

        if (bold) setStyle("-fx-font-weight: bold");
    }
}

package cz.jaktoviditoka.projectmagellan.gui;

import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.skin.ScrollPaneSkin;

public class DebugScrollPaneSkin extends ScrollPaneSkin {

    public DebugScrollPaneSkin(ScrollPane scroll) {
        super(scroll);
        registerChangeListener(scroll.hbarPolicyProperty(), p -> {
            // rude .. but visibility is updated in layout anyway
            getHorizontalScrollBar().setVisible(false);
        });
    }

    @Override
    protected double computePrefHeight(double x, double topInset,
                                       double rightInset, double bottomInset, double leftInset) {
        double computed = super.computePrefHeight(x, topInset, rightInset, bottomInset, leftInset);
        if (getSkinnable().getHbarPolicy() == ScrollBarPolicy.AS_NEEDED && getHorizontalScrollBar().isVisible()) {
            // this is fine when horizontal bar is shown/hidden due to resizing
            // not quite okay while toggling the policy
            // the actual visibilty is updated in layoutChildren?
            computed += getHorizontalScrollBar().prefHeight(-1);
        }
        return computed;
    }

    @Override
    protected double computePrefWidth(double x, double topInset,
                                      double rightInset, double bottomInset, double leftInset) {
        double computed = super.computePrefWidth(x, topInset, rightInset, bottomInset, leftInset);
        if (getSkinnable().getVbarPolicy() == ScrollBarPolicy.AS_NEEDED && getVerticalScrollBar().isVisible()) {
            // this is fine when horizontal bar is shown/hidden due to resizing
            // not quite okay while toggling the policy
            // the actual visibilty is updated in layoutChildren?
            computed += getVerticalScrollBar().prefWidth(-1);
        }
        return computed;
    }

}
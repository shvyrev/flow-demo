package com.vaadin.hummingbird.routing.ui.view;

import com.vaadin.hummingbird.routing.router.Route;
import com.vaadin.hummingbird.routing.router.View;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

@Route(path = "home")
public class HomeView extends CssLayout implements View {

    public HomeView() {
        addComponent(new HTML("<div>Homeh</div>"));
    }

}

package com.vaadin.hummingbird.demo;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HTML;

public class SampleBase extends CssLayout {

    protected CssLayout createSection(CssLayout root, String name) {
        CssLayout sectionRoot = layout(null);
        HTML header = element("h4", name, false);
        CssLayout sectionContent = layout("horizontal-section");
        sectionRoot.addComponents(header, sectionContent);
        root.addComponent(sectionRoot);
        return sectionContent;
    }

    protected HTML style(String styling) {
        HTML style = new HTML("<style></style>");
        style.setInnerHtml(styling);
        return style;
    }

    protected CssLayout root() {
        return layout("horizontal-section-container center-justified layout");
    }

    protected CssLayout layout(String classNames) {
        CssLayout cssLayout = new CssLayout();
        cssLayout.addStyleName(classNames);
        return cssLayout;
    }

    protected HTML element(String tag) {
        return new HTML(new StringBuilder("<").append(tag).append("\"></")
                .append(tag).append(">").toString());
    }

    protected HTML element(String tag, String classNames) {
        return new HTML(new StringBuilder("<").append(tag).append(" class=\"")
                .append(classNames).append("\"></").append(tag).append(">")
                .toString());
    }

    protected HTML element(String tag, String classNames, String innerText) {
        return new HTML(new StringBuilder("<").append(tag).append(" class=\"")
                .append(classNames).append("\">").append(innerText).append("</")
                .append(tag).append(">").toString());
    }

    protected HTML element(String tag, String innerText, boolean b) {
        return new HTML(
                new StringBuilder("<").append(tag).append(">").append(innerText)
                        .append("</").append(tag).append(">").toString());
    }
}

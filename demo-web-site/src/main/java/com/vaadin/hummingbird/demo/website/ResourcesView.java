/*
 * Copyright 2000-2016 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird.demo.website;

import java.io.InputStream;
import java.util.Optional;

import com.vaadin.annotations.Tag;
import com.vaadin.hummingbird.dom.ElementFactory;
import com.vaadin.hummingbird.html.Div;
import com.vaadin.hummingbird.html.HtmlComponent;
import com.vaadin.hummingbird.html.HtmlContainer;
import com.vaadin.hummingbird.router.LocationChangeEvent;
import com.vaadin.hummingbird.router.RouterLink;
import com.vaadin.server.VaadinServletService;
import com.vaadin.ui.Component;
import com.vaadin.ui.PropertyDescriptor;
import com.vaadin.ui.PropertyDescriptors;

/**
 * A view that shows different resources based on the URL.
 *
 * @since
 * @author Vaadin Ltd
 */
public final class ResourcesView extends SimpleView {

    private Div content;
    private IFrame iframe;

    @Tag("iframe")
    private static final class IFrame extends HtmlComponent {
        private static final PropertyDescriptor<String, Optional<String>> srcDescriptor = PropertyDescriptors
                .optionalAttributeWithDefault("src", "");

        private IFrame() {
            getStyle().set("width", "50%");
            getStyle().set("height", "400px");
        }

        void setSrc(String src) {
            set(srcDescriptor, src);
        }
    }

    /**
     * Creates a new view.
     */
    public ResourcesView() {
        add(getMappingInfo(SiteRouterConfigurator.MAPPING_RESOURCE));

        iframe = new IFrame();
        content = new Div();
        content.setClassName("content");

        HtmlContainer links = new HtmlContainer("ul");
        links.add(createLink("", "<none>"));
        links.add(createLink("css/site.css"));
        links.add(createLink("images/vaadin-logo-small.png"));

        HtmlContainer header = new HtmlContainer("p");
        HtmlContainer strong = new HtmlContainer("strong");
        strong.setText("Select the resource to display");
        header.add(strong);

        content.getElement().appendChild(
                ElementFactory.createSpan("Select the resource to display"));
        content.add(links, header, iframe);

        add(content);
    }

    private static Component createLink(String resource) {
        return createLink(resource, resource);
    }

    private static Component createLink(String resource, String caption) {
        RouterLink link = new RouterLink(caption, ResourcesView.class,
                resource);
        HtmlContainer li = new HtmlContainer("li");
        li.add(link);
        return li;
    }

    @Override
    public void onLocationChange(LocationChangeEvent locationChangeEvent) {
        String resourcePath = locationChangeEvent.getPathWildcard();
        if (resourceExists("/" + resourcePath)) {
            iframe.setSrc(resourcePath);
            content.getElement().setChild(3, iframe.getElement());
        } else if ("".equals(resourcePath)) {
            content.getElement().setChild(3,
                    ElementFactory.createDiv("No resource selected"));
        } else {
            content.getElement().setChild(3, ElementFactory
                    .createDiv("Resource " + resourcePath + " not available"));
        }
    }

    private static boolean resourceExists(String resourcePath) {
        InputStream stream = VaadinServletService.getCurrentServletRequest()
                .getServletContext().getResourceAsStream(resourcePath);
        return stream != null;
    }

}

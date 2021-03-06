/*
 * Copyright 2000-2017 Vaadin Ltd.
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
package com.vaadin.flow.demo.dynamicmenu;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.vaadin.flow.component.HtmlContainer;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.demo.dynamicmenu.backend.DataService;
import com.vaadin.flow.demo.dynamicmenu.data.Category;
import com.vaadin.flow.demo.dynamicmenu.data.Product;

/**
 * Menu displaying categories as groups and products as items in the groups.
 *
 * @author Vaadin
 * @since
 */
public final class Menu extends Div {

    private Map<Integer, CategoryMenuItem> categories = new HashMap<>();

    /**
     * Creates the menu.
     */
    public Menu() {
        setClassName("menu");
        HtmlContainer ul = new HtmlContainer("ul");
        add(ul);
        for (Category c : DataService.get().getAllCategories()) {
            CategoryMenuItem item = new CategoryMenuItem(c.getId(),
                    c.getName());
            categories.put(c.getId(), item);
            ul.add(item);
        }
    }

    /**
     * Updates menu selection with the given {@code categoryId} and
     * {@code productId}.
     *
     * @param categoryId
     *            the category id
     * @param productId
     *            the product id
     */
    public void updateSelection(int categoryId, int productId) {
        if (categoryId == -1 && productId != -1) {
            Optional<Product> p = DataService.get().getProductById(productId);
            if (p.isPresent()) {
                categoryId = p.get().getCategory().getId();
            }
        }

        if (categoryId != -1) {
            Optional<CategoryMenuItem> category = getElementForCategory(
                    DataService.get().getCategoryById(categoryId));
            if (category.isPresent()) {
                categories.values().stream().filter(e -> e != category.get())
                        .forEach(e -> selectCategory(e, false));
                selectCategory(category.get(), true);
                category.get().selectProduct(productId);
            }
        }
    }

    private void selectCategory(CategoryMenuItem category, boolean select) {
        if (select) {
            category.expand();
        } else {
            category.collapse();
        }
        category.select(select);
    }

    private Optional<CategoryMenuItem> getElementForCategory(
            Optional<Category> category) {
        if (!category.isPresent()) {
            return Optional.empty();
        }

        return Optional.ofNullable(categories.get(category.get().getId()));
    }
}

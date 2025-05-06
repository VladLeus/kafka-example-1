package org.example;


import com.google.gson.Gson;

public sealed interface Event permits OrderPlacedEvent, OrderCanceledEvent, InventoryEvent {
    Gson GSON = new Gson();

    default String toJson() {
        return GSON.toJson(this);
    }
}

package com.jstudio.panionline.model.eventbus;

public class SendCartItemsCountEvent {
    int mCartItemsCount;

    public SendCartItemsCountEvent(int mCartItemsCount) {
        this.mCartItemsCount = mCartItemsCount;
    }

    public int getmCartItemsCount() {
        return mCartItemsCount;
    }
}

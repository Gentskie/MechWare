package com.example.mechware.Helper;

public class ViewRecordsHelper {
    public String item_id, label, parent_reference, user_type;

    public ViewRecordsHelper(String item_id, String label, String parent_reference, String user_type) {
        this.item_id = item_id;
        this.label = label;
        this.parent_reference = parent_reference;
        this.user_type = user_type;
    }

    public ViewRecordsHelper() {
    }

    public String getItem_id() {
        return item_id;
    }

    public void setItem_id(String item_id) {
        this.item_id = item_id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getParent_reference() {
        return parent_reference;
    }

    public void setParent_reference(String parent_reference) {
        this.parent_reference = parent_reference;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }
}

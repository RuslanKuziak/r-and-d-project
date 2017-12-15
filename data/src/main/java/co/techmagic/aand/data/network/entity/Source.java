package co.techmagic.aand.data.network.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ruslankuziak on 12/15/17.
 */

public class Source {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
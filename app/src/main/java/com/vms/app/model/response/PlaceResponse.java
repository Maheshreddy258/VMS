package com.vms.app.model.response;

import com.google.gson.annotations.SerializedName;

public class PlaceResponse {
@SerializedName("result")
    public ResultModel result;

    public class ResultModel {
        @SerializedName("formatted_address")
        public String formatted_address;
        @SerializedName("geometry")
        public SearchLocationResponse.Geometry geometry;
    }
}

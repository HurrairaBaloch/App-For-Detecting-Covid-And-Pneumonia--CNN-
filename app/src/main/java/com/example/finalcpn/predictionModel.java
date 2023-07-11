package com.example.finalcpn;
public class predictionModel {
    private String imageUrl;
    private String predictionResult;
//    private String id;/

    public predictionModel() {}

    public predictionModel(String imageUrl, String predictionResult) {
        this.imageUrl = imageUrl;
        this.predictionResult = predictionResult;
    }
//     this method allows external code to access the value of the imageUrl
//     variable from an instance of the predictionModel class. By making the method public,
//     any code that has access to an instance of the class can
//     call this method to retrieve the image URL.

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPredictionResult() {
        return predictionResult;
    }


    public void setPredictionResult(String predictionResult) {
        this.predictionResult = predictionResult;
    }
}

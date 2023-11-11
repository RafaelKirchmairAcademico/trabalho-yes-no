package br.ifsul.yesorno;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ApiModel {
    @SerializedName("answer")
    @Expose
    private String answer;

    @SerializedName("forced")
    @Expose
    private boolean forced;

    @SerializedName("image")
    @Expose
    private String image;

    public String getAnswer() {
        return answer;
    }

}

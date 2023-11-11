package br.ifsul.yesorno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private final String URL = "https://yesno.wtf/";
    private Retrofit retrofitYesOrNo;
    private Button botao;
    private TextView respostaApi;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botao = findViewById(R.id.botaoApi);
        respostaApi = findViewById(R.id.respostaApi);
        progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.GONE);

        retrofitYesOrNo = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        botao.setOnClickListener(this);

    }

    private void consultarAPI() {
        RestService restService = retrofitYesOrNo.create(RestService.class);

        Call<ApiModel> call = restService.consultar();

        progressBar.setVisibility(View.VISIBLE);

        call.enqueue(new Callback<ApiModel>() {
            @Override
            public void onResponse(Call<ApiModel> call, Response<ApiModel> response) {
                if (response.isSuccessful()) {
                    ApiModel answer = response.body();
                    respostaApi.setText(answer.getAnswer());
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<ApiModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Ocorreu um erro: " + t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.botaoApi) {
            consultarAPI();
        }
    }
}
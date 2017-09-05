package br.com.bhl.superfid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.FloatRange;
import android.view.View;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import agency.tango.materialintroscreen.animations.IViewTranslation;

public class IntroducaoActivity extends MaterialIntroActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        enableLastSlideAlphaExitTransition(true);

        getNextButtonTranslationWrapper()
                .setEnterTranslation(new IViewTranslation() {
                    @Override
                    public void translate(View view, @FloatRange(from = 0, to = 1.0) float percentage) {
                        view.setAlpha(percentage);
                    }
                });

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccent)
                .buttonsColor(R.color.colorPrimary)
                .image(R.drawable.figure1)
                .title("\nBem-Vindo ao SupeRFiD App!")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .image(R.drawable.figure2)
                .title("\nPara iniciar suas compras primeiro você precisará \nparear seu dispositivo com \num carrinho ou cesta")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccent)
                .buttonsColor(R.color.colorPrimary)
                .image(R.drawable.figure3)
                .title("\nAcompanhe o andamento de suas compras em \ntempo real")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.figure4)
                .buttonsColor(R.color.colorAccent)
                .title("\nVocê também poderá finalizar a compra pelo aplicativo")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorAccent)
                .buttonsColor(R.color.colorPrimary)
                .image(R.drawable.figure5)
                .title("\nConsulte seu histórico de compras quando quiser")
                .build());

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .image(R.drawable.figure6)
                .buttonsColor(R.color.colorAccent)
                .title("\nAproveite suas compras \uD83D\uDE03")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();
        chamarMainActivity();
    }

    private void chamarMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}

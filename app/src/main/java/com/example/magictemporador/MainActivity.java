package com.example.magictemporador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView txtTiempo, txtVidas;
    Button iniciar, reiniciar, pausar;
    CountDownTimer contador;
    long tiempoInicial, vidasIniciales;
    boolean flagEnableIniciado, flagEnablePausa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtVidas = findViewById(R.id.txtValorVidas);
        txtTiempo = findViewById(R.id.txtValorTiempo);

        iniciar = (Button) findViewById(R.id.btnIniciar);
        reiniciar = (Button) findViewById(R.id.btnReiniciar);
        pausar = (Button) findViewById(R.id.btnPausar);

        tiempoInicial = 30;
        vidasIniciales = 1;

        setFlagEnableIniciado(true);
        setFlagEnablePausa(false);


        iniciarContador();

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFlagEnableIniciado(false);
                pausar.setEnabled(true);
                iniciarTemporizador();
            }
        });

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reiniciarTemporizador();
            }
        });

        pausar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFlagEnableIniciado(true);
                pausar.setEnabled(false);
                pausarTemporizador();
            }
        });
    }

    private void iniciarContador() {

        long tiempo = tiempoInicial;
        long minutos = tiempo / 60;
        long segundos = tiempo - minutos * 60;
        long tiempoTotal = segundos + minutos * 60;


        contador = new CountDownTimer(tiempoTotal * 1000, 1000) {

            @Override
            public void onTick(long l) {
                long tiempo = l / 1000;
                long minutos = tiempo / 60;
                long segundos = tiempo - minutos * 60;

                formatearTiempo(minutos, segundos);
            }

            @Override
            public void onFinish() {
                tiempoInicial = tiempoInicial + 10;
                long minutos = tiempoInicial / 60;
                long segundos = tiempoInicial - minutos * 60;

                formatearTiempo(minutos, segundos);
                vidasIniciales++;

                txtVidas.setText(vidasIniciales + "");

                setFlagEnableIniciado(true);


            }
        };
    }

    private void pausarTemporizador() {
        contador.cancel();
    }

    private void reiniciarTemporizador() {
        if (isFlagEnableIniciado()){
            setFlagEnableIniciado(false);
        }
        setFlagEnablePausa(true);

        contador.cancel();
        contador.start();
    }

    private void iniciarTemporizador() {
        contador.start();
    }

    private void formatearTiempo(long minutos, long segundos) {

        String tiempo;
        if (minutos < 10) {
            tiempo = "0" + minutos;
        } else {
            tiempo = minutos + "";
        }
        tiempo = tiempo + ":";

        if (segundos < 10) {
            tiempo = tiempo + "0" + segundos;
        } else {
            tiempo = tiempo + segundos + "";
        }


        txtTiempo.setText(tiempo);
    }

    private boolean isFlagEnableIniciado() {
        return flagEnableIniciado;
    }

    private void setFlagEnableIniciado(boolean valor) {
        flagEnableIniciado = valor;
        iniciar.setEnabled(valor);
    }

    private boolean isFlagEnablePausa() {
        return flagEnablePausa;
    }

    private void setFlagEnablePausa(boolean valor) {
        flagEnablePausa = valor;
        pausar.setEnabled(valor);
    }
}
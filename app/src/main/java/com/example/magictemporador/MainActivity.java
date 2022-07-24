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
    long tiempoInicial, vidasIniciales, tiempoPausado;
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
        tiempoPausado = 0;
        vidasIniciales = 1;

        setFlagEnableIniciado(true);
        setFlagEnablePausa(false);


        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setFlagEnableIniciado(false);
                pausar.setEnabled(true);

                iniciarContador(tiempoInicial);
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

    private void iniciarContador(long tiempoAPoner) {

        contador = new CountDownTimer(tiempoAPoner * 1000, 1000) {

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
        TextView aux = findViewById(R.id.txtValorTiempo);
        String tiempo = aux.getText().toString();
        long minuto = Long.parseLong(tiempo.substring(0, tiempo.indexOf(":")));
        long segundos = Long.parseLong(tiempo.substring(tiempo.indexOf(":") + 1));
        tiempoPausado = minuto * 60 + segundos;
    }

    private void reiniciarTemporizador() {
        if (isFlagEnableIniciado()) {
            setFlagEnableIniciado(false);
        }
        setFlagEnablePausa(true);
        if (!isFlagEnablePausa()) {
        }

        contador.cancel();
        iniciarContador(tiempoInicial);
        contador.start();
    }

    private void iniciarTemporizador() {
        if (tiempoPausado != 0) {
            iniciarContador(tiempoPausado);
            tiempoPausado = 0;
        }
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
package com.example.magictemporador;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    TextView txtTiempo, txtVidas;
    Button iniciar, reiniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final long[] tiempoActual = {30};

        txtVidas = findViewById(R.id.txtValorVidas);
        txtTiempo = findViewById(R.id.txtValorTiempo);
        iniciar = (Button) findViewById(R.id.btnIniciar);
        reiniciar = (Button) findViewById(R.id.btnReiniciar);

        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valorTiempo = txtTiempo.getText().toString();
                int valorVidas = Integer.parseInt(txtVidas.getText().toString());
                long segundos = Long.parseLong(valorTiempo.substring(valorTiempo.indexOf(":") + 1));
                long minutos = Long.parseLong(valorTiempo.substring(0, valorTiempo.indexOf(":")));
                long tiempoTotal = segundos + minutos * 60;

                new CountDownTimer(tiempoTotal * 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        long minutos = (l / 1000) / 60;
                        long segundos = (l / 1000) - minutos;

                        formatearTiempo(minutos, segundos);
                    }

                    @Override
                    public void onFinish() {
                        long nuevoTiempo = tiempoTotal + 10;
                        long minutos = nuevoTiempo / 60;
                        nuevoTiempo = nuevoTiempo - minutos * 60;
                        long segundos = nuevoTiempo;
                        int nuevasVidas = valorVidas + 1;

                        formatearTiempo(minutos, segundos);

                        tiempoActual[0] = tiempoTotal + 10;
                        txtVidas.setText(nuevasVidas + "");
                    }
                }.start();

            }

        });

        reiniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CountDownTimer(tiempoActual[0] * 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        long minutos = (l / 1000) / 60;
                        long segundos = (l / 1000) - minutos;

                        formatearTiempo(minutos, segundos);
                    }

                    @Override
                    public void onFinish() {
                        long nuevoTiempo = tiempoActual[0] + 10;
                        long minutos = nuevoTiempo / 60;
                        nuevoTiempo = nuevoTiempo - minutos * 60;
                        long segundos = nuevoTiempo;

                        formatearTiempo(minutos, segundos);

                        tiempoActual[0] = tiempoActual[0] + 10;
                    }
                };
                long minutos = tiempoActual[0] / 60;
                tiempoActual[0] = tiempoActual[0] - minutos * 60;
                long segundos = tiempoActual[0];

                formatearTiempo(minutos, segundos);
            }
        });
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

}
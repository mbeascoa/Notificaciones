package com.example.notificaciones;

import androidx.appcompat.app.AppCompatActivity;

import androidx.work.Data;
import androidx.work.WorkManager;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.UUID;



public class MainActivity extends AppCompatActivity {

    private Button btnGuardar, btnEliminar, selefecha, selehora;
    private TextView tvfecha, tvhora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        selefecha = findViewById(R.id.btn_selefecha);
        selehora = findViewById(R.id.btn_selehora);
        tvfecha = findViewById(R.id.tv_fecha);
        tvhora = findViewById(R.id.tv_hora);

        Calendar actual = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();

        selefecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int anio = actual.get(Calendar.YEAR);
                int mes = actual.get(Calendar.MONTH);
                int dia = actual.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        calendar.set(Calendar.DAY_OF_MONTH, d);
                        calendar.set(Calendar.MONTH, m);
                        calendar.set(Calendar.YEAR, y);
                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                        String strDate = format.format(calendar.getTime());
                        tvfecha.setText(strDate);
                    }
                }, anio, mes, dia);
                datePickerDialog.show();
            }
        });

        selehora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hora = actual.get(Calendar.HOUR_OF_DAY);
                int minutos = actual.get(Calendar.MINUTE);


                TimePickerDialog timePickerDialog = new TimePickerDialog(v.getContext(), new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int h, int m) {
                        calendar.set(Calendar.DAY_OF_MONTH, h);
                        calendar.set(Calendar.MONTH, m);
                        tvhora.setText(String.format("%02d:%02d", h, m));
                    }
                }, hora, minutos,true);
                timePickerDialog.show();
            }
        });


    btnGuardar = findViewById(R.id.btn_guardar);

        btnGuardar.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        String tag1 = generateKey();
        Long Alerttime = calendar.getTimeInMillis() - System.currentTimeMillis();
        String alerta = String.valueOf(Alerttime);
        Toast.makeText(MainActivity.this, alerta , Toast.LENGTH_SHORT).show();
        int random = (int) (Math.random() * 50 + 1);
        Data data = GuardarData("Notificacion Alerta Tarea", "Este es el detalle", random);
        Workmanagernoti.GuardarNoti(Alerttime, data, "tag1");
        Toast.makeText(MainActivity.this, "Alarma Guardada.", Toast.LENGTH_SHORT).show();
    }
    });
    btnEliminar = findViewById(R.id.btn_eliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener()

    {
        @Override
        public void onClick (View view){
        EliminarNoti("tag1");
        Toast.makeText(MainActivity.this, "Alarma Eliminada.", Toast.LENGTH_SHORT).show();
    }
    });

}

    private String generateKey() {
        return UUID.randomUUID().toString();
    }


    private void EliminarNoti(String tag) {
        WorkManager.getInstance(this).cancelAllWorkByTag(tag);
        Toast.makeText(MainActivity.this, "Alarma Eliminada!", Toast.LENGTH_SHORT).show();
    }


    private Data GuardarData(String titulo, String detalle, int idnoti) {
        return new Data.Builder()
                .putString("titulo", titulo)
                .putString("detalle", detalle)
                .putInt("idnoti", idnoti).build();
    }

}

package com.example.notificaciones;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.UUID;

import static java.time.temporal.TemporalAdjusters.*;
import static java.time.DayOfWeek.*;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

public class MainActivity extends AppCompatActivity {
    private Button btnNotification, btnEliminar;
    private EditText mDateEditText, mTimeEditText

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // filed that places the Date as string, from mUserReminderDate
        mDateEditText = (EditText) dialogView.findViewById(R.id.newTodoDateEditText);

        //Listener to Date picker
        mDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;


                date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(DialogNewNote.this, year, month, day);
                datePickerDialog.show(getActivity().getFragmentManager(), "DateFragment");
            }
        });

        //field where we put the time from mUserReminderDate
        mTimeEditText = (EditText) dialogView.findViewById(R.id.newTodoTimeEditText);

        // listener to Edit Time
        mTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date date;
                hideKeyboard(mToDoTextBodyTitle);
                hideKeyboard(mToDoTextBodyDescription);
                date = new Date();

                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = TimePickerDialog.newInstance(DialogNewNote.this, hour, minute, DateFormat.is24HourFormat(getContext()));
                timePickerDialog.show(getActivity().getFragmentManager(), "TimeFragment");
            }
        });


        btnNotification = findViewById(R.id.btn_guardar);

        btnNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tag1 = generateKey();
                Long Alerttime = calendar.getTimeInMillis() - System.currenTimeMillis();
                int random = (int) (Math.random() * 50 + 1);
                Data data = GuardarData("Notificacion Alerta Tarea", "Este es el detalle", random);
                Workmanagernoti.GuardarNoti(Alerttime, data, "tag1");
                Toast.makeText(MainActivity.this, "Alarma Guardada.", Toast.LENGTH_SHORT).show();
            }
        });
        btnEliminar = findViewById(R.id.btn_eliminar);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EliminarNoti("tag1");
                Toast.makeText(MainActivity.this, "Alarma Eliminada.", Toast.LENGTH_SHORT).show();
            }
        });

        private void EliminarNoti (String tag){
            WorkManager.getInstance(this).cancelAllWorkByTag(tag);
            Toast.makeText(MainActivity.this, "Alarma Eliminada!", Toast.LENGTH_SHORT).show();
        }
        private String generatekey () {
            return UUID.randomUUID().toString();
        }


        private Data GuardarData (String titulo, String detalle,interface id_noti){
            return new Data.Builder()
                    .putString("titulo", titulo)
                    .putString("detalle", detalle)
                    .putInt("id_noti", id_noti).build();
        }



    }

}

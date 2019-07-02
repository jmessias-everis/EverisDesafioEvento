package com.everis.persistenciaSQLite.Mask;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;

public class Pickers extends AppCompatActivity {
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

//    public static void PickerHorario(final TextView txtHora, final Object classe){
//        txtHora.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View view) {
//                Calendar calendarTime = Calendar.getInstance();
//                int tHora = calendarTime.get(Calendar.HOUR_OF_DAY);
//                int tMinuto = calendarTime.get(Calendar.MINUTE);
//                TimePickerDialog timePickerDialog;
//                timePickerDialog = new TimePickerDialog(classe.this, new TimePickerDialog.OnTimeSetListener() {
//                    @Override
//                    public void onTimeSet(TimePicker timePicker, int hora, int minuto) {
//                       String horarioFormatado = hora + ":" + String.format("%02d", minuto);
//                       txtHora.setText(horarioFormatado);
//                    }
//                },tHora, tMinuto, true);
//                timePickerDialog.setTitle("Horário do Evento!");
//                timePickerDialog.show();
//            }
//        });
//    }

    public void verificarCamposVazios(){

    }
}


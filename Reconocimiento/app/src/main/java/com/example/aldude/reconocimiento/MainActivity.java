package com.example.aldude.reconocimiento;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    // Procederemos a escribir en el AndroidManifest:
    //<uses-permission android:name="android.permission.READ_CONTACTS"/>
    //<uses-permission android:name="android.permission.WRITE_CONTACTS"/>
    //<uses-permission android:name="android.permission.CALL_PHONE"/>
    //Declaramos la variable grabar
    TextView grabar;
    //Declaramos la variable RECOGNIZE_SPEECH_ACTIVITY, nos servirá más adelante para el funcionamiento;
    private static final int RECOGNIZE_SPEECH_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Enlazamos la variable con su id correspondiente
        grabar = (TextView)findViewById(R.id.txtGrabarVoz);
    }

    //Aquí empezamos escribiendo protected y automaticamente nos aparecerá un listado de todas las opciones,
    // Elegiremos onActivityResult con sus complementos y creará la línea con sus funciones
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Creamos un switch que contenga el "requestCode" de las líneas anteriores
        //Puede que salga error al escribirlo, pero es porque aún no se escribe nada
        switch (requestCode){
            //Declaramos el case con la variable escrita al inicio "RECOGNIZE_SPEECH_ACTIVITY"
            case RECOGNIZE_SPEECH_ACTIVITY:
                //Aquí creamos una condicional donde lo que se hable por el micro del celular se volverá texto
                if (resultCode == RESULT_OK && null != data){
                    ArrayList<String> speech = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    //Se crea la variable strSpeech2Text
                    String strSpeech2Text = speech.get(0);
                    //Se asocia el String con el TextView grabar declarado anteriormente
                    grabar.setText(strSpeech2Text);
                }

                break;
            default:
                break;
        }
    }

    //Creamos el método Onclick desde el layout, en este caso nuestro método se llama "onClickImgBtnHablar"
    //Aquí le daremos las funcionalidades
    public void ImgBtnHablar(View view) {
        Intent intentActionRecognizeSpeech = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        // Configura el Lenguaje (Español-España)
        intentActionRecognizeSpeech.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "spa-ES");


        try {
            startActivityForResult(intentActionRecognizeSpeech,RECOGNIZE_SPEECH_ACTIVITY);
        }

        catch (ActivityNotFoundException a){
            //Aquí estableceremos que de no ser el caso en el que nuestro dispositivo móvil cuente con el sensor de reconocimiento de voz
            // Entonces nos aparecera el mensaje "Tu dispositivo no soporta el reconocimiento por voz"
            Toast.makeText(getApplicationContext(),"Tu dispositivo no soporta el reconocimiento por voz", Toast.LENGTH_SHORT).show();
        }

    }
}

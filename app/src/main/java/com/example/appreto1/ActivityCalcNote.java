package com.example.appreto1;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;


import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

public class ActivityCalcNote extends AppCompatActivity implements View.OnClickListener{

    TextView oper, resul;
    MaterialButton bC, bOB, bCB, b0, b1, b2, b3, b4, b5, b6, b7, b8, b9;
    MaterialButton butSum, butRes, butMul, butDiv, butIgual;

    MaterialButton bAc, bPunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calcnote);



        resul = findViewById(R.id.idTvResul);
        oper = findViewById(R.id.idTvOper);

        asignarId(bOB, R.id.idButOpenBracket);
        asignarId(bCB, R.id.idButCloseBracket);

        asignarId(bC, R.id.idButC);
        asignarId(b0, R.id.idBut0);
        asignarId(b1, R.id.idBut1);
        asignarId(b2, R.id.idBut2);
        asignarId(b3, R.id.idBut3);
        asignarId(b4, R.id.idBut4);
        asignarId(b5, R.id.idBut5);
        asignarId(b6, R.id.idBut6);
        asignarId(b7, R.id.idBut7);
        asignarId(b8, R.id.idBut8);
        asignarId(b9, R.id.idBut9);

        asignarId(butSum, R.id.idButSuma);
        asignarId(butRes, R.id.idButResta);
        asignarId(butMul, R.id.idButMulti);
        asignarId(butDiv, R.id.idButDividir);
        asignarId(butIgual, R.id.idButResul);
        asignarId(bAc, R.id.idButAC);
        asignarId(bPunto, R.id.idButPunto);

    }

    void asignarId (MaterialButton but, int id){
        but = findViewById(id);
        but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        MaterialButton but = (MaterialButton) v;
        String botonTexto = but.getText().toString();
        String calcular = oper.getText().toString();

        if(botonTexto.equals("AC")){
            oper.setText("");
            resul.setText("0");
            return;
        }

        if(botonTexto.equals("=")){
            oper.setText(resul.getText());
            return;
        }

        if(botonTexto.equals("C")){
            calcular = calcular.substring(0, calcular.length()-1);
        }else{
            calcular = calcular + botonTexto;
        }

        oper.setText(calcular);

        String resultadoFinal = getResult(calcular);

        if(!resultadoFinal.equals("Error")){
            resul.setText(resultadoFinal);
        }
    }

    String getResult (String data){
        try{
            Context con = Context.enter();
            con.setOptimizationLevel(-1);
            Scriptable scrip = con.initSafeStandardObjects();
            String finalResul = con.evaluateString(scrip, data, "Javascript", 1, null).toString();
            if(finalResul.endsWith(".0")){
                finalResul = finalResul.replace(".0", "");
            }
            return finalResul;
        }catch(Exception e){
            return "Error";
        }
    }
}
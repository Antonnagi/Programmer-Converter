package com.example.programmer_calcultor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    private lateinit var decimal: EditText
    private lateinit var binary: EditText
    private lateinit var octal: EditText
    private lateinit var hexa: EditText
    private lateinit var clear:Button
    private var textWatcher: TextWatcher? = null
    private lateinit var focusId: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textWatcher = ViewTextWatcher()
        init()
        callbacks()

    }

    private fun init() {
        decimal = findViewById(R.id.Decimal)
        binary = findViewById(R.id.Binary)
        octal = findViewById(R.id.Octal)
        hexa = findViewById(R.id.Hexa)
        clear=findViewById(R.id.Clear)

        decimal.onFocusChangeListener = FocusViewListener()
        binary.onFocusChangeListener = FocusViewListener()
        octal.onFocusChangeListener = FocusViewListener()
        hexa.onFocusChangeListener = FocusViewListener()
    }

fun callbacks(){
    clear.setOnClickListener {
        clear()
    }
}

    private fun clear() {
        decimal.text.clear()
        binary.text.clear()
        octal.text.clear()
        hexa.text.clear()

    }

    private fun converting(number: String) {
        when (focusId.id) {
            decimal.id -> {
                binary.setText(converter(System.DECIMAL, System.BINARY, number))
                octal.setText(converter(System.DECIMAL, System.OCTAL, number))
                hexa.setText( converter(System.DECIMAL, System.HEXA, number))

            }
            binary.id -> {
                decimal.setText(converter(System.BINARY, System.DECIMAL, number))
                octal.setText(converter(System.BINARY, System.OCTAL, number))
                hexa.setText(converter(System.BINARY, System.HEXA, number))

            }
            octal.id -> {
                decimal.setText(converter(System.OCTAL, System.DECIMAL, number))
                binary.setText(converter(System.OCTAL, System.BINARY, number))
                hexa.setText(converter(System.OCTAL, System.HEXA, number))
            }
            hexa.id -> {
                decimal.setText(converter(System.HEXA, System.DECIMAL, number))
                binary.setText(converter(System.HEXA, System.BINARY, number))
                octal.setText(converter(System.HEXA, System.OCTAL, number))

            }

        }
    }



    private fun converter(sourceBase: System, targetBase: System, numberString: String): String {
        val number = numberString.toBigIntegerOrNull(sourceBase.base)
        return if (number == null)  ""
        else number.toString(targetBase.base)

    }

    inner class ViewTextWatcher : TextWatcher {

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            converting(text.toString())
        }
        override fun afterTextChanged(p0: Editable?) {
        }
    }

    inner class FocusViewListener : View.OnFocusChangeListener {
        override fun onFocusChange(view: View?, hasFocus: Boolean) {
            if (hasFocus) {
                focusId = view as EditText
                focusId.addTextChangedListener(textWatcher)
            } else {
                focusId.removeTextChangedListener(textWatcher)
            }
        }
    }


}
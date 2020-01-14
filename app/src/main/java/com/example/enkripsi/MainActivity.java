package com.example.enkripsi;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText input,kunci;
    private TextView output;
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final int keyCesar= 3;
        kunci = (EditText) findViewById(R.id.kunci);
        input = (EditText) findViewById(R.id.teks);
        Button enkripsi = (Button) findViewById(R.id.enkrip);
        Button dekripsi = (Button) findViewById(R.id.decrip);
        output= (TextView) findViewById(R.id.hasil);

        output.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplication(),"Teks berhasil di copy",Toast.LENGTH_SHORT).show();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("label", output.getText().toString());
                clipboard.setPrimaryClip(clip);

            }
        });

        enkripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kunci.getText().toString().isEmpty()){
                    Toast.makeText(getApplication(),"Masukkan kunci !",Toast.LENGTH_SHORT).show();
                }else {
                    String teks = input.getText().toString().trim();
                    String key = kunci.getText().toString().trim().toUpperCase();
                    String tocesar = encryptCaesarCipher(teks, keyCesar);
                    Log.e("1", tocesar);
                    String enc = encryptVigenere(tocesar, key);
                    Log.e("2", enc);
                    output.setText(enc);
                }
            }
        });

        dekripsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (kunci.getText().toString().isEmpty()){
                    Toast.makeText(getApplication(),"Masukkan kunci !",Toast.LENGTH_SHORT).show();
                }else {
                    String teks = input.getText().toString().trim();
                    String key = kunci.getText().toString().trim().toUpperCase();
                    String tovigenere = decryptVigenere(teks, key);
                    String dec = decryptCaesarCipher(tovigenere, keyCesar);
                    output.setText(dec.toUpperCase());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate menu resource file.
        getMenuInflater().inflate(R.menu.menu, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String teks = output.getText().toString();

                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, teks);
                startActivity(Intent.createChooser(intent, "Share"));
                return false;
            }
        });
        // Return true to display menu
        return true;
    }

    static String encryptVigenere(String text, final String key) {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

    static String decryptVigenere(String text, final String key) {
        String res = "";
        text = text.toUpperCase();
        for (int i = 0, j = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res += (char)((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

    public static String encryptCaesarCipher(String plainText, int shiftKey)
    {
        plainText = plainText.toLowerCase();
        String cipherText = "";
        for (int i = 0; i < plainText.length(); i++)
        {
            char replaceVal = plainText.charAt(i);
            int charPosition = ALPHABET.indexOf(replaceVal);
            if(charPosition != -1) {
                int keyVal = (shiftKey + charPosition) % 26;
                replaceVal = ALPHABET.charAt(keyVal);
            }

            cipherText += replaceVal;
        }
        return cipherText;
    }

    public static String decryptCaesarCipher(String cipherText, int shiftKey)
    {
        cipherText = cipherText.toLowerCase();
        String plainText = "";
        for (int i = 0; i < cipherText.length(); i++)
        {
            char replaceVal = cipherText.charAt(i);
            int charPosition = ALPHABET.indexOf(replaceVal);
            if(charPosition != -1) {
                int keyVal = (charPosition - shiftKey) % 26;
                if (keyVal < 0) {
                    keyVal = ALPHABET.length() + keyVal;
                }
                replaceVal = ALPHABET.charAt(keyVal);
            }
            plainText += replaceVal;
        }
        return plainText;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}

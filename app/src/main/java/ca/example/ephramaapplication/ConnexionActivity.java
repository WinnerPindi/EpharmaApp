package ca.example.ephramaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ConnexionActivity extends AppCompatActivity {

    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://epharmaapplication-default-rtdb.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        //Déclaration des variables
        final EditText email= findViewById(R.id.user_email);
        final EditText password = findViewById(R.id.password);
        final Button loginBtn = findViewById(R.id.connexionButton);
        final Button registerNowBtn = findViewById(R.id.registerNowBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Recuperation des valeurs entrées
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();

                if(emailTxt.isEmpty() || passwordTxt.isEmpty()){
                    Toast.makeText(ConnexionActivity.this,"Entrez votre email ou votre mot de passe",Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //On verifie si le email existe dans Firebase

                            if(snapshot.hasChild(emailTxt)){
                                //le email existe dans firebase
                                //On verifie que le mot de passe entré correspond à celui qui est dans firebase
                                final String getPassword = snapshot.child(emailTxt).child("password").getValue(String.class);
                                if(getPassword.equals(passwordTxt)){
                                    Toast.makeText(ConnexionActivity.this,"Connexion réussi !",Toast.LENGTH_SHORT).show();
                                    //Ouverture de l'activité principale(Page d'acceuil)
                                    startActivity(new Intent(ConnexionActivity.this,MainActivity.class));
                                    finish();
                                }
                                else {
                                    Toast.makeText(ConnexionActivity.this,"Mot de passe incorrect",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(ConnexionActivity.this,"Mot de passe incorrect",Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }

                registerNowBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Ouverture de l'activité d'inscription
                        startActivity(new Intent(ConnexionActivity.this,InscriptionActivity.class));
                    }
                });
            }
        });
    }
}
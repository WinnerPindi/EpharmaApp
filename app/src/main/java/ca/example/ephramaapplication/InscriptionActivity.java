package ca.example.ephramaapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class InscriptionActivity extends AppCompatActivity {

    //Creation des objets de reference pour acceder à firebase
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://epharmaapplication-default-rtdb.firebaseio.com/");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);


        //Declaration des variales
        final EditText username = findViewById(R.id.username);
        final EditText email = findViewById(R.id.userEmail);
        final EditText password = findViewById(R.id.userPassword);
        final EditText conPassword = findViewById(R.id.userConfirmPassword);

        final Button registerBtn = findViewById(R.id.registerBtn);
        final Button loginNowBtn = findViewById(R.id.loginNow);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //On recupère les donées entrées par l'utilisateur
                final String usernameTxt = username.getText().toString();
                final String emailTxt = email.getText().toString();
                final String passwordTxt = password.getText().toString();
                final String conPasswordTxt = conPassword.getText().toString();

                //On verifie que l'utilisateur a rempli tous les champs avant d'envoyer les données à firebase

                if (usernameTxt.isEmpty() || emailTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty()){
                    Toast.makeText(InscriptionActivity.this,"Veuillez completer les champs", Toast.LENGTH_SHORT).show();
                }

                // On vérifie si le mot de passe est égal à sa confimation
                else if(!passwordTxt.equals(conPasswordTxt)){
                    Toast.makeText(InscriptionActivity.this,"Les mots de passes sont incorrectes",Toast.LENGTH_SHORT).show();
                }

                else{
                    databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //On verifie si l'utilisateur n'est pas déjà enregistré
                            if (snapshot.hasChild(usernameTxt)){
                                Toast.makeText(InscriptionActivity.this, "Ce nom est déjà utilisé", Toast.LENGTH_SHORT).show();
                            }

                            else{
                                //Envoi des données à firebase
                                databaseReference.child("users").child(usernameTxt).child("username").setValue(usernameTxt);
                                databaseReference.child("users").child(usernameTxt).child("email").setValue(emailTxt);
                                databaseReference.child("users").child(usernameTxt).child("password").setValue(passwordTxt);
                                //Message pour signaler àl'utilisateur que l'enregistrement a été éffectuer
                                Toast.makeText(InscriptionActivity.this, "Enregistré avec succès", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }



            }
        });


        //

        loginNowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }


}
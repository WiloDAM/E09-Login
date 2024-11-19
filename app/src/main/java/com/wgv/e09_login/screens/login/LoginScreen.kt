package com.wgv.e09_login.screens.login

import android.annotation.SuppressLint
import android.media.Image
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.wgv.e09_login.R
import com.wgv.e09_login.model.User
import com.wgv.e09_login.navigation.Screens
import javax.annotation.CheckReturnValue
import kotlin.contracts.contract
import kotlin.math.sign


@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewMode = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val showLoginForm = rememberSaveable { mutableStateOf(true) }

    //GOOGLE
    //Este token se consigue en FireBase -> Proveedor de acceso ->Conf del SDK -> Id del cliente web
    val token = "798304136419-mglel8sq5964caqntli7tbtbhvhboh5g.apps.googleusercontent.com"
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts
            .StartActivityForResult() // esto abrirá un activity para hacer el login de Google
    ) {
        val task =
            GoogleSignIn.getSignedInAccountFromIntent(it.data) // esto lo facilita la librería añadida
        // el intent será enviado cuando se lance el launcher
        try {
            val account = task.getResult(ApiException::class.java)
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            viewModel.signInWithGoogleCredential(credential) {
                navController.navigate(Screens.HomeScreen.name)
            }
        } catch (ex: Exception) {
            Log.d("My Login", "GoogleSignIn falló")
        }
    }

    Surface(modifier = Modifier.fillMaxSize()) {

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize().background(Color.Black)

            ) {

                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Column {
                        Image(
                            painter = painterResource(R.drawable.descarga),
                            contentDescription = "Logo",
                            modifier = Modifier.clip(CircleShape)
                        )
                        Text(
                            text = "Spotify",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 23.sp,
                            color = Color.White
                        )
                    }

                }

                Spacer(modifier = Modifier.height(50.dp))

                // si es true crea la cuenta, sino incia la sesion
                if (showLoginForm.value) {
                    UserForm(isCreateAccount = false) { email, password ->
                        Log.d("My Login", "Logueado con $email y $password")
                        viewModel.signInWithEmailAndPassword(
                            email,
                            password
                        ) {
                            navController.navigate(Screens.HomeScreen.name)
                        }

                    }
                } else {
                    UserForm(isCreateAccount = true) { email, password ->
                        Log.d("My Login", "Logueado con $email y $password")
                        viewModel.createUserWithEmailAndPassword(
                            email,
                            password
                        ) {
                            navController.navigate(Screens.HomeScreen.name)
                        }
                    }


                }

                //alternar entre Crear cuenta e iniciar sesión

                Spacer(modifier = Modifier.height(20.dp))


                //GOOGLE
                Column {
                    Text(text = "or sign up with ", color = Color.Green)
                    IconButton(onClick = {
                        val opciones =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()

                        val googleSignInCliente = GoogleSignIn.getClient(context, opciones)
                        launcher.launch(googleSignInCliente.signInIntent)
                    }, modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Image(
                            painter = painterResource(R.drawable.icono_google),
                            contentDescription = "Imagen"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val text1 = if (showLoginForm.value) "Don't have an account?"
                    else "Do you have an account?"
                    val text2 = if (showLoginForm.value) "Sign up"
                    else "Sign in"
                    Text(text = text1, color = Color.Gray)
                    Text(
                        text = text2,
                        modifier = Modifier
                            .clickable {
                                showLoginForm.value = !showLoginForm.value
                            }
                            .padding(start = 5.dp), color = Color.Green
                    )
                }
                Spacer(modifier = Modifier.height(140.dp))

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom
                    ) {


                        Image(
                            painter = painterResource(R.drawable.spotify_inferior),
                            contentDescription = "LogoFinal",
                            modifier = Modifier.size(80.dp)
                        )


                }

            }

        }



}

@Composable
fun UserForm(
    isCreateAccount: Boolean,
    onDone: (String, String) -> Unit = { email, pwd -> }
) {
    val email = rememberSaveable { mutableStateOf("") }

    val password = rememberSaveable { mutableStateOf("") }

    val passwordVisible = rememberSaveable { mutableStateOf(false) }

    val valido = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    //controla que al hacer click en el boton submit, el teclcado se oculta
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        EmailInput(emailState = email)
        PasswordIput(
            passwordState = password,
            passwordVisible = passwordVisible
        )
        Spacer(modifier = Modifier.height(30.dp))
        SubmitButtom(
            textId = if (isCreateAccount) "Sign up" else "Sign in",
            inputValido = valido
        ) {
            onDone(email.value.trim(), password.value.trim())

            // se oculta el teclado, el ? es que se llama a la funcion en modo seguro
            keyboardController?.hide()
        }
    }
}


@Composable
fun EmailInput(
    emailState: MutableState<String>,
    labelId: String = "Email"
) {

    InputField(
        valuestate = emailState,
        labelId = labelId,
        keyboardType = KeyboardType.Email
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    valuestate: MutableState<String>,
    labelId: String,
    keyboardType: KeyboardType,
    isSingleLine: Boolean = true
) {
    OutlinedTextField(
        value = valuestate.value,
        onValueChange = { valuestate.value = it },
        label = { Text(text = labelId) },
        singleLine = isSingleLine,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordIput(
    passwordState: MutableState<String>,
    passwordVisible: MutableState<Boolean>,
    labelId: String = "Password"
) {

    val visualTransformation = if (passwordVisible.value)
        VisualTransformation.None
    else PasswordVisualTransformation()

    OutlinedTextField(
        value = passwordState.value,
        onValueChange = { passwordState.value = it },
        label = { Text(text = labelId) },
        singleLine = true,
        modifier = Modifier
            .padding(bottom = 10.dp, start = 10.dp, end = 10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        visualTransformation = visualTransformation, colors = TextFieldDefaults.outlinedTextFieldColors(
            unfocusedBorderColor = Color.Gray,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        trailingIcon = {
            if (passwordState.value.isNotBlank()) {
                PasswordVisibleIcon(passwordVisible)
            } else null
        }
    )
}

@Composable
fun PasswordVisibleIcon(passwordVisible: MutableState<Boolean>) {
    val image = if (passwordVisible.value)
        Icons.Default.VisibilityOff
    else
        Icons.Default.Visibility

    IconButton(onClick = { passwordVisible.value = !passwordVisible.value }) {
        Icon(
            imageVector = image,
            contentDescription = ""
        )
    }
}

@Composable
fun SubmitButtom(textId: String, inputValido: Boolean, onClic: () -> Unit) {

    Button(
        onClick = onClic,
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(Color.Green),
        enabled = inputValido
    ) {
        Text(
            text = textId,
            modifier = Modifier.padding(5.dp),
            fontSize = 20.sp
        )
    }
}




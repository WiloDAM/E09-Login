package com.wgv.e09_login

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wgv.e09_login.navigation.Navigation
import com.wgv.e09_login.ui.theme.E09LoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            E09LoginTheme {

                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 20.dp),
                    color = MaterialTheme.colorScheme.background,
                ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally

                        ) { Navigation() }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    E09LoginTheme {
        Scaffold(modifier = Modifier.fillMaxSize(), containerColor = Color.Black) { innerPadding ->
            MyApp(modifier = Modifier.padding(innerPadding))
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(modifier: Modifier) {
    var texto by rememberSaveable { mutableStateOf("") }
    var contra by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 60.dp)
    ) {

        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 20.dp)
        ) {
            Column {
                TextField(
                    value = texto,
                    onValueChange = {},
                    label = { Text(text = "Username", color = Color.Gray) },
                    modifier = Modifier.padding(vertical = 20.dp),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Black,
                    )
                )
                TextField(
                    value = contra,
                    onValueChange = {},
                    label = { Text("Password", color = Color.Gray) },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Black
                    )
                )

                Text(
                    text = "Forget password?",
                    color = Color.Gray,
                    fontSize = 13.sp,
                    modifier = Modifier.align(Alignment.End)
                )
            }

        }

        Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
            Column {

                Button(
                    onClick = {},
                    modifier = Modifier
                        .width(280.dp)
                        .padding(vertical = 20.dp),
                    colors = ButtonDefaults.buttonColors(Color.Green)
                )
                {
                    Text(text = "Sign in", fontSize = 19.sp)
                }

                Text(
                    text = "or sign up with",
                    color = Color.Green, modifier = Modifier.align(Alignment.CenterHorizontally)
                )


            }


        }

        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 30.dp)
        ) {
            Row {
                Text(
                    text = "Don't have an account?",
                    color = Color.Gray
                )
                Text(
                    text = "Sign up",
                    color = Color.Green, modifier = Modifier.clickable { }
                )
            }


        }


    }

}
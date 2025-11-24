package com.classify.hublink.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cases
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.classify.hublink.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(authViewModel: AuthViewModel = viewModel()) {
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.padding(20.dp)
    ) {
        Column (
            modifier = Modifier.verticalScroll(rememberScrollState())
        ){

            Box(
                modifier = Modifier
                    .fillMaxWidth().align(Alignment.CenterHorizontally)
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Nombre de Usuario",
                    textAlign = TextAlign.Center
                )
            }

                Column(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                ) {
                    Text(
                        text = "Información Personal",
                        fontSize = 25.sp,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User"
                        )
                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Nombre:\nAlessandro"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Mail,
                            contentDescription = "Email"
                        )
                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Correo electrónico: ${authViewModel.userEmail}"
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = "Phone"
                        )
                        Spacer(modifier = Modifier.width(15.dp))

                        Text(
                            text = "Numero de telefono: \n+57 3013257023"
                        )
                    }
                }

            Column(
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Formación e Intereses",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.School,
                        contentDescription = "University"
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Universidad:\nUniversidad Francisco de Paula Santander"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Work,
                        contentDescription = "Program"
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Carrera: \nIngenieria de Sistemas"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.RocketLaunch,
                        contentDescription = "Hobbies"
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Intereses: \nMe gusta aprender cosas nuevas"
                    )
                }
            }

            Column(
                modifier = Modifier
                    .padding(vertical = 30.dp)
            ) {
                Text(
                    text = "Redes Sociales",
                    fontSize = 25.sp,
                    modifier = Modifier.padding(bottom = 20.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "User"
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Linkedin:\n wilfredoperez32"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Mail,
                        contentDescription = "Email"
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "Github: \nwilfredoperez32"
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Phone,
                        contentDescription = "Phone"
                    )
                    Spacer(modifier = Modifier.width(15.dp))

                    Text(
                        text = "GitLab: \nwilfredoperez32"
                    )
                }
            }

            Button(
                onClick = {
                    scope.launch {
                        authViewModel.signOut()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sign Out")
            }
        }
    }
}
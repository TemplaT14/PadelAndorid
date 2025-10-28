package com.joseemarti.padelscorejoseemarti

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.joseemarti.padelscorejoseemarti.ui.theme.PadelScoreJoseEMartiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PadelScoreJoseEMartiTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MarcadorPadel()
                }
            }
        }
    }
}

@Composable
fun MarcadorPadel() {
    var setsA by remember { mutableStateOf(0) }
    var setsB by remember { mutableStateOf(0) }
    var juegosA by remember { mutableStateOf(0) }
    var juegosB by remember { mutableStateOf(0) }
    var puntosA by remember { mutableStateOf(0) }
    var puntosB by remember { mutableStateOf(0) }
    var tiePuntosA by remember { mutableStateOf(0) }
    var tiePuntosB by remember { mutableStateOf(0) }
    var partidoTerminado by remember { mutableStateOf(false) }
    var tieBreakActivo by remember { mutableStateOf(false) }

    val puntosTexto = listOf("0", "15", "30", "40")

    fun resetTodo() {
        setsA = 0; setsB = 0
        juegosA = 0; juegosB = 0
        puntosA = 0; puntosB = 0
        tiePuntosA = 0; tiePuntosB = 0
        partidoTerminado = false
        tieBreakActivo = false
    }

    fun comprobarSet() {
        if (!tieBreakActivo) {
            if (juegosA == 6 && juegosB == 6) {
                tieBreakActivo = true
                tiePuntosA = 0; tiePuntosB = 0
            } else if (juegosA >= 6 && juegosA - juegosB >= 2) {
                setsA++; juegosA = 0; juegosB = 0
            } else if (juegosB >= 6 && juegosB - juegosA >= 2) {
                setsB++; juegosA = 0; juegosB = 0
            }
        }

        if (setsA == 2 || setsB == 2) partidoTerminado = true
    }

    fun comprobarJuego() {
        if (tieBreakActivo) {
            // Tie-break
            if ((tiePuntosA >= 7 && tiePuntosA - tiePuntosB >= 2)) {
                setsA++
                juegosA = 0; juegosB = 0
                tieBreakActivo = false
                tiePuntosA = 0; tiePuntosB = 0
            } else if ((tiePuntosB >= 7 && tiePuntosB - tiePuntosA >= 2)) {
                setsB++
                juegosA = 0; juegosB = 0
                tieBreakActivo = false
                tiePuntosA = 0; tiePuntosB = 0
            }
        } else {
            // Juego normal
            if (puntosA >= 3 && puntosB >= 3) {
                if (puntosA == puntosB + 1) {
                    juegosA++; puntosA = 0; puntosB = 0
                } else if (puntosB == puntosA + 1) {
                    juegosB++; puntosA = 0; puntosB = 0
                }
            } else {
                if (puntosA > 3 && puntosA - puntosB >= 1) {
                    juegosA++; puntosA = 0; puntosB = 0
                } else if (puntosB > 3 && puntosB - puntosA >= 1) {
                    juegosB++; puntosA = 0; puntosB = 0
                }
            }
        }
        comprobarSet()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_general)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Sets
        Text(
            text = stringResource(R.string.sets),
            fontSize = dimensionResource(R.dimen.texto_header).value.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.naranja_header))
                .padding(dimensionResource(R.dimen.padding_texto_header))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "$setsA",
                fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                color = colorResource(R.color.azul_team)
            )
            Text(
                text = "$setsB",
                fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                color = colorResource(R.color.verde_team)
            )
        }

        // Juegos
        Text(
            text = stringResource(R.string.games),
            fontSize = dimensionResource(R.dimen.texto_header).value.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.naranja_header))
                .padding(dimensionResource(R.dimen.padding_texto_header))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(
                text = "$juegosA",
                fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                color = colorResource(R.color.azul_team)
            )
            Text(
                text = "$juegosB",
                fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                color = colorResource(R.color.verde_team)
            )
        }

        // Puntos / Tie-break
        Text(
            text = stringResource(R.string.actual),
            fontSize = dimensionResource(R.dimen.texto_header).value.sp,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.naranja_header))
                .padding(dimensionResource(R.dimen.padding_texto_header))
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            if (tieBreakActivo) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Tie-break",
                        fontSize = dimensionResource(R.dimen.texto_header).value.sp,
                        color = Color.Black
                    )
                    Text(
                        "$tiePuntosA",
                        fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                        color = colorResource(R.color.azul_team)
                    )
                }
            } else {
                Text(
                    if (puntosA < 4) puntosTexto[puntosA] else "40",
                    fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                    color = colorResource(R.color.azul_team)
                )
            }
            if (tieBreakActivo) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Tie-break",
                        fontSize = dimensionResource(R.dimen.texto_header).value.sp,
                        color = Color.Black
                    )
                    Text(
                        "$tiePuntosB",
                        fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                        color = colorResource(R.color.verde_team)
                    )
                }
            } else {
                Text(
                    if (puntosB < 4) puntosTexto[puntosB] else "40",
                    fontSize = dimensionResource(R.dimen.texto_puntos).value.sp,
                    color = colorResource(R.color.verde_team)
                )
            }
        }

        // Ganador
        if (partidoTerminado) {
            Text(
                text = if (setsA == 2) stringResource(R.string.winner_a)
                else stringResource(R.string.winner_b),
                fontSize = dimensionResource(R.dimen.texto_ganador).value.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Botones puntos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.peque)),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (!partidoTerminado) {
                        if (tieBreakActivo) tiePuntosA++ else puntosA++
                        comprobarJuego()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.azul_team))
            ) {
                Text(stringResource(R.string.point_a), color = Color.Black)
            }

            Button(
                onClick = {
                    if (!partidoTerminado) {
                        if (tieBreakActivo) tiePuntosB++ else puntosB++
                        comprobarJuego()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.verde_team))
            ) {
                Text(stringResource(R.string.point_b), color = Color.Black)
            }
        }

        // Botón Reset
        Button(
            onClick = { resetTodo() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimensionResource(id = R.dimen.boton)),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.rojo_reset))
        ) {
            Text(stringResource(R.string.reset))
        }
    }
}

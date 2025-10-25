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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
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
fun SectionLabel(label: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorResource(R.color.naranja_header))
            .padding(dimensionResource(R.dimen.padding_section)),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            text = label,
            fontSize = dimensionResource(R.dimen.font_size_header).value.sp,
            color = colorResource(R.color.black),
            fontWeight = FontWeight.Bold
        )
    }
}
@Composable
fun ScoreRow(
    scoreA: Int,
    scoreB: Int,
    colorA: Color,
    colorB: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(R.dimen.spacing_medium)),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            text = "$scoreA",
            fontSize = dimensionResource(R.dimen.font_size_number).value.sp,
            color = colorA,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$scoreB",
            fontSize = dimensionResource(R.dimen.font_size_number).value.sp,
            color = colorB,
            fontWeight = FontWeight.Bold
        )
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
            .padding(0.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Sets
        SectionLabel(stringResource(R.string.sets))
        ScoreRow(
            scoreA = setsA,
            scoreB = setsB,
            colorA = colorResource(R.color.azul_team),
            colorB = colorResource(R.color.verde_team)
        )


        // Juegos
        SectionLabel(stringResource(R.string.games))
        ScoreRow(
            juegosA, juegosB,
            colorResource(R.color.azul_team),
            colorResource(R.color.verde_team)
        )

        // Puntos / Tie-break
        SectionLabel(stringResource(R.string.actual))
        if (tieBreakActivo) {
            Text(
                stringResource(R.string.tie_break),
                color = colorResource(R.color.rojo_reset),
                fontSize = dimensionResource(R.dimen.font_size_winner).value.sp,
                fontWeight = FontWeight.Bold
            )
            ScoreRow(tiePuntosA, tiePuntosB,
                     colorResource(R.color.azul_team),
                     colorResource(R.color.verde_team)
            )
        } else {
            ScoreRow(
                puntosA, puntosB,
                colorResource(R.color.azul_team),
                colorResource(R.color.verde_team)
            )
        }

        // Ganador
        if (partidoTerminado) {
            Text(
                text = if (setsA == 2) stringResource(R.string.winner_a)
                else stringResource(R.string.winner_b),
                fontSize = 22.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }

        // Botones puntos
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 1.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = {
                    if (!partidoTerminado) {
                        if (tieBreakActivo) tiePuntosA++ else puntosA++
                        comprobarJuego()
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.azul_team))
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
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.verde_team))
            ) {
                Text(stringResource(R.string.point_b), color = Color.Black)
            }
        }

        // Botón Reset
        Button(
            onClick = { resetTodo() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.rojo_reset))
        ) {
            Text(stringResource(R.string.reset))
        }
    }
}

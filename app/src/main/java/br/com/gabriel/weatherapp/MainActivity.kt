package br.com.gabriel.weatherapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.gabriel.weatherapp.ui.theme.WeatherAppTheme
import br.com.gabriel.weatherapp.ui.theme.WeatherViewModel
import coil.compose.AsyncImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: WeatherViewModel by viewModels()

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppTheme(darkTheme = true) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val padding = innerPadding.calculateTopPadding() + 20.dp

                    WeatherCard(
                        viewModel = viewModel,
                        modifier = Modifier
                            .padding(padding)

                    )
                }
            }
        }
    }
}

@Composable
fun WeatherCard(viewModel: WeatherViewModel, modifier: Modifier = Modifier) {
    val weather by viewModel.weather

    var input by remember {
        mutableStateOf("")
    }

    val city = remember {
        State()
    }

    val temp: String = if (weather.current?.temp_c == null) {
        ""
    } else {
        weather.current?.temp_c.toString()
    }

    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Weather App",
            style = TextStyle(fontSize = 35.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp)
        )

        OutlinedTextField(
            modifier = Modifier.padding(bottom = 10.dp, top = 10.dp),
            value = input,
            onValueChange = { value ->
                input = value
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                viewModel.fetchWeather(input)
            }),
            label = { Text(text = "Type the City") })
        Button(modifier = Modifier.padding(10.dp), onClick = {
            viewModel.fetchWeather(input)
            city.text = weather.location?.name.toString()
        }) {
            Text(text = "Search")
        }
        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
                .height(65.dp)
                .height(65.dp)
                .background(
                    brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF018786),
                            Color(0xFF03DAC6)
                        )
                    ),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(10.dp)
        ) {
            Text(
                modifier = Modifier.align(Alignment.TopStart),
                style = TextStyle(
                    fontSize = 25.sp, fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(
                        Font(R.font.poppins_medium, FontWeight.Medium)
                    ),
                ), text = weather.location?.name ?: ""
            )
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                style = TextStyle(
                    fontSize = 20.sp, fontWeight = FontWeight.Medium,
                    fontFamily = FontFamily(
                        Font(R.font.poppins_medium, FontWeight.Medium)
                    ),
                ),
                text = weather.current?.condition?.text ?: ""
            )
        }
        Box(
            modifier = Modifier

                .padding(30.dp)
                .height(150.dp)
                .size(150.dp)
                .background(
                    brush = Brush.linearGradient(listOf(Color(0xFFBB86FC), Color(0xFF3700B3))),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(5.dp)
        ) {
            AsyncImage(
                model = "https:${weather.current?.condition?.icon}",
                modifier = Modifier
                    .size(75.dp)
                    .align(Alignment.TopStart),
                contentDescription = "Image"
            )
            Text(
                modifier = Modifier.align(Alignment.BottomEnd),
                style = TextStyle(fontSize = 40.sp, fontWeight = FontWeight.Bold),
                text = "$temp °C",
//            text = "${weather.current?.temp_c.toString()} °C" ? ""
            )

        }

    }

    DisposableEffect(Unit) {
        viewModel.fetchWeather()
        onDispose { }
    }
}

class State {
    var text: String by mutableStateOf("")
}

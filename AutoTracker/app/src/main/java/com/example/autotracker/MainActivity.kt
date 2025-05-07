//==================================================================================
//AxelAuto Tracker Version 4
// Last Revised 5/4/2025

package com.example.autotracker



//                              -- IMPORTS --

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.autotracker.ui.theme.ColorMode
import com.example.autotracker.ui.theme.ThemeManager
import kotlinx.coroutines.delay

//===============================================================================

//                               -- PROGRAM NAVIGATION --
//                                   (for developers)
//
//               ****Updated Current to V.4****
//  Contents:
//            - Main Program    -   Line 72 - 89
//            - App Home        -   Line 90 - 191
//            - Vehicles Hub    -   Line 192 - 312
//            - View Vehicles   -   Line 313 - 478
//            - Add Vehicles    -   Line 479 - 699
//            - Car Details     -   Line 700 - 823
//            - Mini Screens    -   Line 824 - 918
//            - Settings        -   Line 919 - 1022
//            - Services Hub    -   Line 1023 - 1095
//            - New Service     -   Line 1096 - 1219
//            - View Services   -   Line 1220 - 1369
//            - Data Classes    -   Line 1370 - 1407
//            - PlaceHolders    -   Line 1408 - 1475
//            - Version Control -   Line 1476 - 1571
//            - Ideas Hub       -   Line 1572 - 1598

//    **** All Content Designed and Wrote By Alex Johnston, Excluding any Images
//             taken From The Web for School project Purposes Only. ****

//==================================================================================

//                                  -- MAIN PROGRAM --



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            SplashScreen(
                context = context, // Pass context to SplashScreen
                onNavigateToHome = {
                    startActivity(Intent(context, HomeActivity::class.java))
                    finish()
                }
            )
        }
    }
}


//=================================================================================

//                                 -- APP HOME --



class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            HomeScreen(
                context = context, // Pass context to HomeScreen
                onNavigateToVehicles = { startActivity(Intent(context, VehiclesActivity::class.java)) },
                onNavigateToSettings = { startActivity(Intent(context, SettingsActivity::class.java)) },
                onNavigateToServices = { startActivity(Intent(context, ServicesActivity::class.java)) }
            )
        }
    }
}


//-------------------------------------------------------------------------------

@Composable
fun HomeScreen(
    context: Context, // Pass context to play sounds
    onNavigateToVehicles: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToServices: () -> Unit
) {

    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Welcome to AxelAuto Trakker",
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )

            Image(
                painter = painterResource(id = R.drawable.axelautohome),
                contentDescription = "Home Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
                    .padding(bottom = 16.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    playSound(context, R.raw.pressed)
                    onNavigateToVehicles()
                }) {
                    Text("Vehicles")
                }

                Button(onClick = {
                    playSound(context, R.raw.pressed)
                    onNavigateToServices()
                }) {
                    Text("Services")
                }

                Button(onClick = {
                    playSound(context, R.raw.pressed)
                    onNavigateToSettings()
                }) {
                    Text("Settings")
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "Use the buttons above to manage your vehicle records, view maintenance history, or adjust app settings.",
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
            )
        }
    }
}


//===============================================================================

//                             -- VEHICLES SCREEN --

//This is the hub, leading to the important pages that handle the users inputs


class VehiclesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            VehiclesScreen(
                context = context, // Pass context to VehiclesScreen
                onNavigateToAddVehicle = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val intent = Intent(context, AddVehicleActivity::class.java)
                    startActivity(intent)
                },
                onNavigateToViewVehicles = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val intent = Intent(context, ViewVehiclesActivity::class.java)
                    startActivity(intent)
                },
                onNavigateHome = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    val intent = Intent(context, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}


//-------------------------------------------------------------------------------

@Composable
fun VehiclesScreen(
    context: Context, // Pass context to play sounds
    onNavigateToAddVehicle: () -> Unit,
    onNavigateToViewVehicles: () -> Unit,
    onNavigateHome: () -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    val imageResource = when (ThemeManager.themeSelector) {
        1 -> R.drawable.camaro1
        2 -> R.drawable.bike1
        3 -> R.drawable.truck1
        else -> R.drawable.camaro1
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "Vehicles Hub",
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
            )

            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Theme Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = "Go Ahead, Add Your New Hot-rod",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Text(
                text = "We Wont Tell Your Wife",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    onNavigateToAddVehicle()
                }) {
                    Text("Add Vehicle")
                }

                Button(onClick = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    onNavigateToViewVehicles()
                }) {
                    Text("View Vehicles")
                }
            }

            Button(
                onClick = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    onNavigateHome()
                },
                modifier = Modifier.padding(vertical = 16.dp)
            ) {
                Text("Go Back Home")
            }
        }
    }
}



//====================================================================================

//                         --  VIEW VEHICLES SCREEN --


// Where the user can view the vehicle data they saved in the add vehicles screen



class ViewVehiclesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            ViewVehiclesScreen(
                context = context, // Pass context to ViewVehiclesScreen
                onNavigateBack = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    finish()
                }
            )
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}

//-------------------------------------------------------------------------------

@Composable
fun ViewVehiclesScreen(
    context: Context, // Pass context for sound playback
    onNavigateBack: () -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    val imageResource = when (ThemeManager.themeSelector) {
        1 -> R.drawable.camaro2
        2 -> R.drawable.bike2
        3 -> R.drawable.truck2
        else -> R.drawable.camaro2
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "View Vehicles",
                style = MaterialTheme.typography.titleLarge,
                color = textColor,
                modifier = Modifier.padding(vertical = 25.dp)
            )

            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Theme Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            )

            Text(
                text = "View Your Rides",
                color = Color.White,
                modifier = Modifier.padding(vertical = 10.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    playSound(context, R.raw.carstart) // Play "Car Start" sound
                    val intent = Intent(context, CarDetailsActivity::class.java).apply {
                        putExtra("carYear", car1.year)
                        putExtra("carMake", car1.make)
                        putExtra("carModel", car1.model)
                        putExtra("carMileage", car1.mileage)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Car 1")
                }

                Button(onClick = {
                    playSound(context, R.raw.carstart) // Play "Car Start" sound
                    val intent = Intent(context, CarDetailsActivity::class.java).apply {
                        putExtra("carYear", car2.year)
                        putExtra("carMake", car2.make)
                        putExtra("carModel", car2.model)
                        putExtra("carMileage", car2.mileage)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Car 2")
                }

                Button(onClick = {
                    playSound(context, R.raw.carstart) // Play "Car Start" sound
                    val intent = Intent(context, CarDetailsActivity::class.java).apply {
                        putExtra("carYear", car3.year)
                        putExtra("carMake", car3.make)
                        putExtra("carModel", car3.model)
                        putExtra("carMileage", car3.mileage)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Car 3")
                }

                Button(onClick = {
                    playSound(context, R.raw.carstart) // Play "Car Start" sound
                    val intent = Intent(context, CarDetailsActivity::class.java).apply {
                        putExtra("carYear", car4.year)
                        putExtra("carMake", car4.make)
                        putExtra("carModel", car4.model)
                        putExtra("carMileage", car4.mileage)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Car 4")
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    onNavigateBack()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 25.dp)
                    .padding(bottom = 10.dp)
            ) {
                Text("Go Back")
            }
        }
    }
}



//====================================================================================

//                             --  ADD VEHICLES SCREEN --


//This is the screen where users will add their cars information and save it to te app.

//Need to update to save to a permanent storage or database file.
//As of right now, 4/25/2025, all inputs and memory are lost on reboot.



class AddVehicleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            var navigateToCarSaved by remember { mutableStateOf(false) }

            if (navigateToCarSaved) {
                CarSavedScreen(
                    onNavigateBackToVehicles = {
                        playSound(context, R.raw.back) // Play "Back" sound
                        finish()
                    }
                )
            } else {
                AddVehicleScreen(
                    context = context, // Pass context to AddVehicleScreen
                    onNavigateBack = {
                        playSound(context, R.raw.back) // Play "Back" sound
                        finish()
                    },
                    onSaveVehicle = { car, carIndex ->
                        playSound(context, R.raw.pressed) // Play "Pressed" sound
                        when (carIndex) {
                            1 -> {
                                car1.year = car.year
                                car1.make = car.make
                                car1.model = car.model
                                car1.mileage = car.mileage
                            }
                            2 -> {
                                car2.year = car.year
                                car2.make = car.make
                                car2.model = car.model
                                car2.mileage = car.mileage
                            }
                            3 -> {
                                car3.year = car.year
                                car3.make = car.make
                                car3.model = car.model
                                car3.mileage = car.mileage
                            }
                            4 -> {
                                car4.year = car.year
                                car4.make = car.make
                                car4.model = car.model
                                car4.mileage = car.mileage
                            }
                        }
                        navigateToCarSaved = true
                    },
                    onNavigateToCarSaved = {
                        navigateToCarSaved = true
                    }
                )
            }
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}

//-----------------------------------------------------------------------------------

@Composable
fun AddVehicleScreen(
    context: Context,
    onNavigateBack: () -> Unit,
    onSaveVehicle: (Car, Int) -> Unit,
    onNavigateToCarSaved: () -> Unit
) {
    var year by remember { mutableStateOf("") }
    var make by remember { mutableStateOf("") }
    var model by remember { mutableStateOf("") }
    var mileage by remember { mutableStateOf("") }


        val surfaceColor = when (ColorMode.ColorSelector) {
            1 -> Color.DarkGray // Dark Mode
            2 -> Color.White    // Light Mode
            else -> Color.DarkGray // Default fallback
        }

        val textColor = when (ColorMode.ColorSelector) {
            1 -> Color.White // Dark Mode
            2 -> Color.Black // Light Mode
            else -> Color.White // Default fallback
        }


        val imageResource = when (ThemeManager.themeSelector) {
            1 -> R.drawable.camaro3 // Car theme
            2 -> R.drawable.bike3   // Bike theme
            3 -> R.drawable.truck3  // Truck theme
            else -> R.drawable.camaro3 // Default fallback
        }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Add Vehicle",
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Theme Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            TextField(
                value = year,
                onValueChange = { year = it },
                label = { Text("Year") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = make,
                onValueChange = { make = it },
                label = { Text("Make") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = model,
                onValueChange = { model = it },
                label = { Text("Model") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = mileage,
                onValueChange = { mileage = it },
                label = { Text("Mileage") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        playSound(context, R.raw.pressed)
                        val car = Car(year, make, model, mileage)
                        onSaveVehicle(car, 1)
                        onNavigateToCarSaved()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Save to Car 1")
                }
                Button(
                    onClick = {
                        playSound(context, R.raw.pressed)
                        val car = Car(year, make, model, mileage)
                        onSaveVehicle(car, 2)
                        onNavigateToCarSaved()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Save to Car 2")
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(
                    onClick = {
                        playSound(context, R.raw.pressed)
                        val car = Car(year, make, model, mileage)
                        onSaveVehicle(car, 3)
                        onNavigateToCarSaved()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Save to Car 3")
                }
                Button(
                    onClick = {
                        playSound(context, R.raw.pressed)
                        val car = Car(year, make, model, mileage)
                        onSaveVehicle(car, 4)
                        onNavigateToCarSaved()
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Save to Car 4")
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go Back")
            }
        }
    }
}



//==================================================================================

//                              -- CAR DETAILS SCREEN --


class CarDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context
        val year = intent.getStringExtra("carYear")
        val make = intent.getStringExtra("carMake")
        val model = intent.getStringExtra("carModel")
        val mileage = intent.getStringExtra("carMileage")

        setContent {
            CarDetailsScreen(
                context = context, // Pass context to CarDetailsScreen
                onNavigateToAddVehicle = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val intent = Intent(context, AddVehicleActivity::class.java)
                    startActivity(intent)
                },
                onNavigateToServices = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val intent = Intent(context, ServicesActivity::class.java)
                    startActivity(intent)
                },
                year = year.orEmpty(),
                make = make.orEmpty(),
                model = model.orEmpty(),
                mileage = mileage.orEmpty(),
                onNavigateBack = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    finish()
                }
            )
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}


//-----------------------------------------------------------------------------------

@Composable
fun CarDetailsScreen(
    context: Context, // Pass context for sound playback
    onNavigateToServices: () -> Unit,
    onNavigateToAddVehicle: () -> Unit,
    year: String,
    make: String,
    model: String,
    mileage: String,
    onNavigateBack: () -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    val imageResource = when (ThemeManager.themeSelector) {
        1 -> R.drawable.camaro4
        2 -> R.drawable.bike4
        3 -> R.drawable.truck4
        else -> R.drawable.camaro4
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Theme Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Text(
                text = "Car Details",
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Year: $year", color = textColor)
            Text(text = "Make: $make", color = textColor)
            Text(text = "Model: $model", color = textColor)
            Text(text = "Mileage: $mileage", color = textColor)

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onNavigateToAddVehicle()
            }) {
                Text("Edit Vehicle")
            }

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onNavigateToServices()
            }) {
                Text("Add Service")
            }

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateBack()
            }) {
                Text("Go Back")
            }
        }
    }
}


//=================================================================================


//                             -- MINI SCREENS --

fun playSound(context: Context, soundResId: Int) {
    val mediaPlayer = MediaPlayer.create(context, soundResId)
    mediaPlayer?.start()
    mediaPlayer?.setOnCompletionListener {
        it.release() // Free resources after playing
    }
}

// ----------------------------- Start up Screen -------------------------------


@Composable
fun SplashScreen(
    context: Context, // Pass context for sound playback
    onNavigateToHome: () -> Unit
) {
    LaunchedEffect(Unit) {
        playSound(context, R.raw.intro) // Play "Intro" sound
        delay(4000L) // Wait 4 seconds before navigating
        onNavigateToHome()
    }

    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "AxelAuto Trakker",
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
        }
    }
}




//   ---------------------------  Car Saved Screen  ----------------------
//
//          An in-between screen showing that you successfully saved the
//                           vehicle information


@Composable
fun CarSavedScreen(onNavigateBackToVehicles: () -> Unit) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray // Dark Mode
        2 -> Color.White    // Light Mode
        else -> Color.DarkGray // Default fallback
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White // Dark Mode
        2 -> Color.Black // Light Mode
        else -> Color.White // Default fallback
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Car Saved",
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateBackToVehicles,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go to Vehicles")
            }
        }
    }
}


//==================================================================================

//                             --SETTINGS ACTIVITY--

class SettingsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            SettingsScreen(
                context = context, // Pass context to SettingsScreen
                onNavigateBack = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    finish()
                },
                onNavigateToHome = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}

//---------------------------------------------------------------------------------

@Composable
fun SettingsScreen(
    context: Context, // Pass context for sound playback
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Settings Screen",
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Theme Selection",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                ThemeManager.themeSelector = 1
            }) {
                Text("Car Theme")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                ThemeManager.themeSelector = 2
            }) {
                Text("Bike Theme")
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                ThemeManager.themeSelector = 3
            }) {
                Text("Truck Theme")
            }
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Day/Night Modes",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                ColorMode.ColorSelector = 1
            }) {
                Text("Dark Mode")
            }

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                ColorMode.ColorSelector = 2
            }) {
                Text("Light Mode")
            }
            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateToHome()
            }) {
                Text("Go Home")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateBack()
            }) {
                Text("Go Back")
            }
        }
    }
}

//================================================================================
//                            -- SERVICES SCREEN--

class ServicesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            ServicesScreen(
                context = context, // Pass context to ServicesScreen
                onNavigateBack = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    finish()
                },
                onNavigateToHome = {
                    playSound(context, R.raw.back) // Play "Back" sound
                    val intent = Intent(context, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
                },
                onNavigateToNewService = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val intent = Intent(context, NewServiceActivity::class.java)
                    startActivity(intent)
                },
                onNavigateToViewServices = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val intent = Intent(context, ViewServicesActivity::class.java)
                    startActivity(intent)
                }
            )
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}


//-------------------------------------------------------------------------------

@Composable
fun ServicesScreen(
    context: Context, // Pass context for sound playback
    onNavigateToHome: () -> Unit,
    onNavigateBack: () -> Unit,
    onNavigateToNewService: () -> Unit,
    onNavigateToViewServices: () -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }
    val imageResource = R.drawable.service1

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {    Image(
        painter = painterResource(id = imageResource),
        contentDescription = "Services Background",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )

        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Services Screen", style = MaterialTheme.typography.titleLarge, color = textColor)

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onNavigateToNewService()
            }) { Text("Add Service") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onNavigateToViewServices()
            }) { Text("View Services") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateBack()
            }) { Text("Go Back") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateToHome()
            }) { Text("Go Home") }
        }
    }
}

//================================================================================
//                               --NEW SERVICE SCREEN--

class NewServiceActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            var navigateToServiceSaved by remember { mutableStateOf(false) }

            if (navigateToServiceSaved) {
                playSound(context, R.raw.back) // Play "Back" sound
                finish()
            } else {
                NewServiceScreen(
                    context = context, // Pass context to NewServiceScreen
                    onNavigateBack = {
                        playSound(context, R.raw.back) // Play "Back" sound
                        finish()
                    },
                    onSaveService = { serviceEntry, carIndex ->
                        playSound(context, R.raw.pressed) // Play "Pressed" sound
                        when (carIndex) {
                            1 -> ServiceStorage2.car1Services.add(serviceEntry)
                            2 -> ServiceStorage2.car2Services.add(serviceEntry)
                            3 -> ServiceStorage2.car3Services.add(serviceEntry)
                            4 -> ServiceStorage2.car4Services.add(serviceEntry)
                        }
                        navigateToServiceSaved = true
                    }
                )
            }
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}

//--------------------------------------------------------------------------------------------

@Composable
fun NewServiceScreen(
    context: Context, // Pass context for sound playback
    onNavigateBack: () -> Unit,
    onSaveService: (ServiceEntry, Int) -> Unit
) {
    var vehicle by remember { mutableStateOf("") }
    var serviceType by remember { mutableStateOf("") }
    var serviceDate by remember { mutableStateOf("") }
    var serviceCost by remember { mutableStateOf("") }

    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }
    val imageResource = R.drawable.service2 // Adding "services3" image

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Image(
        painter = painterResource(id = imageResource),
        contentDescription = "Services Background",
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
    )
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(25.dp))
            Text(text = "Add Service", style = MaterialTheme.typography.titleLarge, color = textColor)

            TextField(value = serviceType, onValueChange = { serviceType = it }, label = { Text("Service Type") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = serviceDate, onValueChange = { serviceDate = it }, label = { Text("Date") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(16.dp))

            TextField(value = serviceCost, onValueChange = { serviceCost = it }, label = { Text("Cost") }, modifier = Modifier.fillMaxWidth())
            Spacer(modifier = Modifier.height(32.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val serviceEntry = ServiceEntry(vehicle, serviceType, serviceDate, serviceCost)
                    onSaveService(serviceEntry, 1)
                }) { Text("Save to Car 1") }

                Button(onClick = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val serviceEntry = ServiceEntry(vehicle, serviceType, serviceDate, serviceCost)
                    onSaveService(serviceEntry, 2)
                }) { Text("Save to Car 2") }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val serviceEntry = ServiceEntry(vehicle, serviceType, serviceDate, serviceCost)
                    onSaveService(serviceEntry, 3)
                }) { Text("Save to Car 3") }

                Button(onClick = {
                    playSound(context, R.raw.pressed) // Play "Pressed" sound
                    val serviceEntry = ServiceEntry(vehicle, serviceType, serviceDate, serviceCost)
                    onSaveService(serviceEntry, 4)
                }) { Text("Save to Car 4") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateBack()
            }, modifier = Modifier.fillMaxWidth()) { Text("Go Back") }
        }
    }
}



//==========================================================================================
//                                --VIEW SERVICES--


class ViewServicesActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this // Get the context

        setContent {
            var selectedCar by remember { mutableStateOf<Int?>(null) }

            if (selectedCar == null) {
                VehicleSelectionScreen(
                    context = context, // Pass context to VehicleSelectionScreen
                    onNavigateBack = {
                        playSound(context, R.raw.back) // Play "Back" sound
                        finish()
                    },
                    onSelectCar = {
                        playSound(context, R.raw.pressed) // Play "Pressed" sound
                        selectedCar = it
                    }
                )
            } else {
                ViewServicesScreen(
                    context = context, // Pass context to ViewServicesScreen
                    selectedCar = selectedCar!!,
                    onNavigateBack = {
                        playSound(context, R.raw.back) // Play "Back" sound
                        finish()
                    },
                    onNavigateToNewService = {
                        playSound(context, R.raw.pressed) // Play "Pressed" sound
                        val intent = Intent(context, NewServiceActivity::class.java)
                        startActivity(intent)
                    }
                )
            }
        }
    }

    fun playSound(context: Context, soundResId: Int) {
        val mediaPlayer = MediaPlayer.create(context, soundResId)
        mediaPlayer?.start()
        mediaPlayer?.setOnCompletionListener {
            it.release() // Free resources after playback
        }
    }
}


//------------------------------------------------------------------------------------------

@Composable
fun ViewServicesScreen(
    context: Context, // Pass context for sound playback
    selectedCar: Int,
    onNavigateBack: () -> Unit,
    onNavigateToNewService: () -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    val services = when (selectedCar) {
        1 -> ServiceStorage2.car1Services
        2 -> ServiceStorage2.car2Services
        3 -> ServiceStorage2.car3Services
        4 -> ServiceStorage2.car4Services
        else -> emptyList()
    }
    val carName = when (selectedCar) {
        1 -> "${car1.model}"
        2 -> "${car2.model}"
        3 -> "${car3.model}"
        4 -> "${car4.model}"
        else -> "Unknown Vehicle"
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Services for $carName", style = MaterialTheme.typography.titleLarge, color = textColor)

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                services.forEach { service ->
                    ServiceItem(service, textColor)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onNavigateToNewService()
            }) { Text("Add New Service") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateBack()
            }) { Text("Go Back") }
        }
    }
}


//----------------------------------------------------------------------------------------------

@Composable
fun ServiceItem(service: ServiceEntry, textColor: Color) {
    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        Text(text = "Service: ${service.serviceType}", style = MaterialTheme.typography.bodyLarge, color = textColor)
        Text(text = "Date: ${service.serviceDate}", style = MaterialTheme.typography.bodyLarge, color = textColor)
        Text(text = "Cost: ${service.serviceCost}", style = MaterialTheme.typography.bodyLarge, color = textColor)

        Spacer(modifier = Modifier.height(8.dp))

        Divider(color = Color.Gray, thickness = 1.dp)
    }
}


//--------------------------------------------------------------------------------------------

@Composable
fun VehicleSelectionScreen(
    context: Context, // Pass context for sound playback
    onNavigateBack: () -> Unit,
    onSelectCar: (Int) -> Unit
) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray
        2 -> Color.White
        else -> Color.DarkGray
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White
        2 -> Color.Black
        else -> Color.White
    }

    val imageResource = R.drawable.service3 // Adding "services3" image


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = imageResource),
                contentDescription = "Services Background",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Select a Vehicle", style = MaterialTheme.typography.titleLarge, color = textColor)

            Spacer(modifier = Modifier.height(32.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onSelectCar(1)
            }) { Text("View Car 1 Services") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onSelectCar(2)
            }) { Text("View Car 2 Services") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onSelectCar(3)
            }) { Text("View Car 3 Services") }

            Spacer(modifier = Modifier.height(10.dp))

            Button(onClick = {
                playSound(context, R.raw.pressed) // Play "Pressed" sound
                onSelectCar(4)
            }) { Text("View Car 4 Services") }

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = {
                playSound(context, R.raw.back) // Play "Back" sound
                onNavigateBack()
            }) { Text("Go Back") }
        }
    }
}


//=================================================================================

//                               -- DATA CLASSES --


//----------------------------Car Data Class--------------------------------

data class Car(
    var year: String = "No Data",
    var make: String = "No Data",
    var model: String = "Unknown",
    var mileage: String = "No Data"
)

val car1 = Car()
val car2 = Car()
val car3 = Car()
val car4 = Car()

//---------------------------------------------------------------------------
data class ServiceEntry(
    val vehicle: String,
    val serviceType: String,
    val serviceDate: String,
    val serviceCost: String
)

object ServiceStorage {
    val services = mutableStateListOf<ServiceEntry>()
}
// Storage for service entries per car
object ServiceStorage2 {
    val car1Services = mutableStateListOf<ServiceEntry>()
    val car2Services = mutableStateListOf<ServiceEntry>()
    val car3Services = mutableStateListOf<ServiceEntry>()
    val car4Services = mutableStateListOf<ServiceEntry>()
}

//===================================================================================

//                             -- PLACEHOLDER CLASSES --



//                      These are the ones that still need work
//                                    4/25/2025



//Cannot remember its purpose but it is being used for the time being

//Need to look further into its purpose and rid of this.

class PlaceholderActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlaceholderScreen3(onNavigateBack = { finish() })
        }
    }
}

@Composable
fun PlaceholderScreen3(onNavigateBack: () -> Unit) {
    val surfaceColor = when (ColorMode.ColorSelector) {
        1 -> Color.DarkGray // Dark Mode
        2 -> Color.White    // Light Mode
        else -> Color.DarkGray // Default fallback
    }

    val textColor = when (ColorMode.ColorSelector) {
        1 -> Color.White // Dark Mode
        2 -> Color.Black // Light Mode
        else -> Color.White // Default fallback
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = surfaceColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "No cars in inventory",  //  <-------------- This helps find its purpose
                style = MaterialTheme.typography.titleLarge,
                color = textColor
            )
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onNavigateBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Go Back")
            }
        }
    }
}



//=========================================================================================
//
//                              --App Information--
//
// App Name: AxelAutoTrakker
//
// Developer: Alex Johnston
//
// IvyTech Community College - Mobile Application Development - Spring 2025
//
//=========================================================================================
//
//                                  --Version Control--
//
//
// -------------------------------------Version 1.0 ----------------------------------
//
//  4/10/2024
//
//    Updates:
//
//          Vehicles are able to be added, saved, viewed, and edited.
//
//    Needs:
//
//          Add Service history capabilities
//          Add some minor settings to delete app data, and maybe change the color scheme
//          From light mode to dark mode, as well as showcase developer information.
//          App also needs prettied up with various photos and possible minor animations
//
//
//
// -------------------------------------Version 1.1--------------------------------------
//
//  4/18/2025
//
//    Updates:
//
//         Added More Photos and updated the navigation system as well
//         To enhance the experience and ease of navigating the app. The
//         app has also been prepared to implement the vehicle services
//         by adding the proper navigation buttons to the Car Details Pages.
//
//         Other various fixes to overall better the quality of the application.
//
//         Fixes to Code Organization for easier development.
//
//    Needs:
//
//    - Add The ability for the user to add a service to the vehicle.
//
//    - The service data needs to be specifically linked to the correct vehicle Index.
//
//    - either a document or something needs to be used as a saving device for
//       User inputs.
//
//    - Need to also implement a couple settings options;
//             easy ones that may:
//                        - Go from Dark Mode to Light Mode
//                        - Change the "Theme" :
//                                      -Hot Rods
//                                      -Camaro (Like It is Now)
//                                      -Lamborghini
//                                      -Motor Cycles
//                        - Delete Saved Data :
//                                      - This would be really useful if i can
//                                      - Implement this with my saving device.
//
//     - Need to Decide what we are doing with the history and services Buttons:
//                                                 ^^ (Found on Home screen) ^^
//
//            -These are almost the same thing, need to figure out how to pretty
//             this up while also implementing everything correctly.
//
//
//
//-----------------------------------Version 1.2---------------------------------------------
//
// 4/25/2025
//
//      Updates:
//          - Added the ability to change the theme from Camaros, to Trucks or Motorcycles.
//          - Added the ability to change from light mode to dark mode
//          - All these changes can be now accessed from the new Settings tab found in Home.
//
//      Needs:
//          - Need to add in a database to house user information
//          - Need to add the Services screen
//          - Need to add in the ability to add a new completed service, add an upcoming/needed service,
//              and View both lists of services.
//
//------------------------------------------------------------------------------------------
//
//-----------------------------------Version 1.3---------------------------------------------
//
// 5/4/2025
//
//      Updates:
//          - **Service history is now linked to specific vehicles!**
//          - When adding a service, you select which car it belongs to.
//          - Viewing services now goes through a new **Vehicle Selection Screen** first.
//          - Each car has its own service history, fully separate from the others.
//          - ViewVehiclesScreen now correctly **routes each car to its details and service history**.
//          - Navigation has been cleaned up for a more **intuitive flow**.
//
//      Needs:
//          - Need to persist vehicle and service data when the app is closed.
//          - Need to add a **database** or **file system** for saving user inputs.
//          - Cleanup UI to make selecting and viewing services **easier to follow**.
//          - Maybe add a **quick-view section** for upcoming services per car.
//
//----------------------------------- Version 1.4 -----------------------------------
// 5/12/2025
//
// Updates: Sound effects integration!
//
//Intro sound plays on app startup.
//
//Navigation sounds trigger when moving between screens.
//
//Back navigation sound plays when returning to Home or previous screens.
//
//Car engine start sound plays when selecting a vehicle.
//
// UI enhancements:
//
//Refined spacing and layout for better visibility.
//
//Added high-quality images across all screens.
//
//Ensured images are correctly placed and scaled.
//
// Navigation improvements:
//
//Streamlined screen transitions for smoother user experience.
//
//Cleaned up Home screen button structure.
//
//Services Screen now directs properly to adding and viewing service history.
//
//Needs:
// Still need persistent storage for vehicle and service data.
// Decide whether a database or file system is best for saving user inputs.
// Further refine the UI flow for selecting and editing services.
//
//=======================================================================================
//                                       ---IDEAS HUB-----
//
//      This is a safe place for free floating ideas for the app. Anything goes here,
//                  as long as It stays behind the double slashes //
//
//------------------------------------------------------------------------------
//
// I would like to add pictures of my own vehicle.
//
//------------------------------------------------------------------------------
//
// I would like to add pictures of reciets for repairs and parts purchases.
// That way i can save my reciets, without worrying about ink run, storage,
// or having to save them in my gallery. This way i know they are stored away,
// and i can free up space in my phones gallery for pictures i have taken, instead of
// copies of reciets.
//
//------------------------------------------------------------------------------------
//
// For Fun, may add in the ability to add up the total value of services on each vehicle.
//
//------------------------------------------------------------------------------------
//
//  -- FRESH IDEAS HERE!
//
//====================================================================================
package com.example.appnewkool.ui.product.add

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.appnewkool.R
import com.example.appnewkool.data.model.Product
import com.example.appnewkool.ui.theme.AppNewkoolTheme
import com.example.appnewkool.ui.theme.BlueCus
import com.example.appnewkool.ui.theme.BlueWhite
import com.example.appnewkool.ui.theme.WhiteBlue

@Composable
fun AddProductScreen(
    viewModel: AddProductViewModel? = hiltViewModel(),
    navToHome: () -> Unit,
    navOnBack: () -> Unit
) {
    val product = viewModel?.product ?: Product(
        null, "", null, null, null,
        null, null, null, null, null, null
    )
    BackHandler() {
        navOnBack.invoke()
    }
    if (viewModel?.toast != null) {
        val context = LocalContext.current
        LaunchedEffect(viewModel.toast) {
            Toast.makeText(context, viewModel.toast, Toast.LENGTH_SHORT).show()
        }
    }

    if (viewModel?.isSuccess == true) {
        LaunchedEffect(viewModel.isSuccess) {
            navToHome.invoke()
        }
    }

    Box(modifier = Modifier.background(color = Color.White)) {
        Column(
            modifier = Modifier
                .background(color = BlueCus.copy(alpha = 0.2f))
        ) {

            Box(
                modifier = Modifier
                    .background(
                        color = BlueWhite,
                        shape = RoundedCornerShape(bottomEnd = 60.dp)
                    )
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 15.dp, start = 10.dp, end = 10.dp, bottom = 5.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        IconButton(onClick = navOnBack) {
                            Icon(
                                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back_ios_24),
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                        }

                        IconButton(onClick = navToHome) {
                            Icon(
                                imageVector = Icons.Rounded.Home,
                                contentDescription = "",
                                tint = Color.White,
                                modifier = Modifier
                                    .padding(horizontal = 10.dp)
                            )
                        }
                    }

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 15.dp),
                        text = "NEWKOOL FILM", color = Color.White,
                        fontSize = 40.sp,
                        fontFamily = FontFamily(
                            Font(
                                resId = R.font.modulusmedium,
                                weight = FontWeight.Black
                            )
                        ),
                    )

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 30.dp),
                        contentAlignment = Alignment.BottomEnd
                    ) {
                        Text(
                            text = "Add",
                            color = Color.White,
                            fontSize = 20.sp,
                            fontFamily = FontFamily(
                                Font(
                                    resId = R.font.modulusbold
                                )
                            ),
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
            ) {
                Card(
                    modifier = Modifier
                        .padding(top = 30.dp, start = 30.dp, end = 30.dp),
                    shape = RoundedCornerShape(30.dp),
                    elevation = 20.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp), horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Item("Tên xe", product.tenXe) { viewModel?.onChangeTextTenXe(it) }
                        Item("Hãng xe", product.hangXe) { viewModel?.onChangeTextHangXe(it) }
                        Item("Kính lái", product.kinhLai) { viewModel?.onChangeTextKinhLai(it) }
                        Item(
                            "Sườn trước",
                            product.suonTruoc
                        ) { viewModel?.onChangeTextSuonTruoc(it) }
                        Item("Sườn sau", product.suonSau) { viewModel?.onChangeTextSuonSau(it) }
                        Item(
                            "Khoang sau",
                            product.khoangSau
                        ) { viewModel?.onChangeTextKhoangSau(it) }
                        Item("Kính hậu", product.kinhHau) { viewModel?.onChangeTextKinhHau(it) }
                        Item("Tam giác", product.tamGiac) { viewModel?.onChangeTextTamGiac(it) }
                        Item("Nóc", product.noc) { viewModel?.onChangeTextNoc(it) }
                    }
                }

            }
        }
        if (viewModel?.isLoading == true) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color.Transparent)
                    .clickable { false },
                contentAlignment = Alignment.BottomCenter
            ) {
                CircularProgressIndicator(color = WhiteBlue)
            }

        } else {
            ExtendedFloatingActionButton(
                text = { Text(text = "Create") },
                onClick = { viewModel?.createUser() },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_save),
                        contentDescription = "",
                    )
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(20.dp),
                backgroundColor = BlueCus,
                contentColor = Color.White
            )
        }
    }
}

@Composable
fun Item(label: String, text: String?, onChangeText: (String) -> Unit) {
    OutlinedTextField(
        value = text ?: "",
        onValueChange = onChangeText,
        label = { Text(text = label) },
        colors = TextFieldDefaults.textFieldColors(
            unfocusedLabelColor = Color.Black,
            focusedLabelColor = Color.Black,
            textColor = Color.Black,
            backgroundColor = BlueCus.copy(alpha = 0.2f)
        ),
        shape = RoundedCornerShape(10.dp),
        singleLine = true
    )
}


@Preview(showSystemUi = true)
@Composable
fun PreAddProductScreen() {
    AppNewkoolTheme {
        AddProductScreen(null, navToHome = {}) {}
    }
}
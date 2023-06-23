package com.example.depositsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.depositsapp.ui.theme.DepositsAppTheme
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DepositsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DepTimeLayout()
                }
            }
        }
    }
}

@Composable
fun DepTimeLayout() {
    var amountInput by remember { mutableStateOf("") }
    var roundUp by remember { mutableStateOf(false) }
    var value3 by remember { mutableStateOf(false) }
    var value5 by remember { mutableStateOf(false) }
    var value9 by remember { mutableStateOf(false) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val dep = calculateDep(amount, value3, value5, value9)

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .padding(40.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(R.string.calculate_dep),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            label = R.string.dep_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Send
            ),
            value = amountInput,
            onValueChanged = { amountInput = it },
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth(),
        )
        Text(
            text = stringResource(R.string.date_dep),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(alignment = Alignment.Start)
        )

        threemonth(
            value3 = value3,
            onRoundUpChanged = { value3 = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        sixmonth(
            value5 = value5,
            onRoundUpChanged = { value5 = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        year(
            value9 = value9,
            onRoundUpChanged = { value9 = it },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        Text(
            text = stringResource(R.string.amount, dep),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditNumberField(
    @StringRes label: Int,
    @DrawableRes leadingIcon: Int,
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChanged: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        singleLine = true,
        modifier = modifier,
        onValueChange = onValueChanged,
        label = { Text(stringResource(label)) },
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun threemonth(
    value3: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.threemonthdep))
        RadioButton(
            selected = value3,
            onClick = { onRoundUpChanged(true) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}
@Composable
fun sixmonth(
    value5: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.sixmonthdep))
        RadioButton(
            selected = value5,
            onClick = { onRoundUpChanged(true) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}
@Composable
fun year(
    value9: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.yeardep))
        RadioButton(
            selected = value9,
            onClick = { onRoundUpChanged(true) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

private fun calculateDep(amount: Double, value3:Boolean, value5: Boolean, value9: Boolean): String {
    var dep = amount
    if (value3)
        dep = 3.0 / 100 * amount
    if (value5)
        dep = 5.0 / 100 * amount
    if (value9)
        dep = 9.0 / 100 * amount
    return NumberFormat.getCurrencyInstance().format(dep)
}

@Preview(showBackground = true)
@Composable
fun DepTimeLayoutPreview() {
    DepositsAppTheme() {
        DepTimeLayout()
    }
}

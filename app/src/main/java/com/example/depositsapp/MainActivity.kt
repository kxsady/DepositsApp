package com.example.depositsapp

import DepositViewModel
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
import androidx.lifecycle.SavedStateHandle
import com.example.depositsapp.ui.theme.DepositsAppTheme
import java.text.NumberFormat

val viewModel = DepositViewModel(SavedStateHandle())
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
    var selectedValue by remember { mutableStateOf(0) }

    val amount = amountInput.toDoubleOrNull() ?: 0.0
    val dep = calculateDep(amount, selectedValue)

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
                imeAction = ImeAction.Next
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
            selected = selectedValue == 3,
            onSelectedChanged = { if (it) selectedValue = 3 },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        sixmonth(
            selected = selectedValue == 5,
            onSelectedChanged = { if (it) selectedValue = 5 },
            modifier = Modifier.padding(bottom = 32.dp)
        )
        year(
            selected = selectedValue == 9,
            onSelectedChanged = { if (it) selectedValue = 9 },
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
    selected: Boolean,
    onSelectedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.threemonthdep))
        RadioButton(
            selected = selected,
            onClick = { onSelectedChanged(true) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}
@Composable
fun sixmonth(
    selected: Boolean,
    onSelectedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.sixmonthdep))
        RadioButton(
            selected = selected,
            onClick = { onSelectedChanged(true) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}
@Composable
fun year(
    selected: Boolean,
    onSelectedChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
    ) {
        Text(text = stringResource(R.string.yeardep))
        RadioButton(
            selected = selected,
            onClick = { onSelectedChanged(true) },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End)
        )
    }
}

private fun calculateDep(amount: Double, selectedValue: Int): String {
    val dep = when (selectedValue) {
        3 -> 3.0 / 100 * amount
        5 -> 5.0 / 100 * amount
        9 -> 9.0 / 100 * amount
        else -> amount
    }
    return NumberFormat.getCurrencyInstance().format(dep)
}

@Preview(showBackground = true)
@Composable
fun DepTimeLayoutPreview() {
    DepositsAppTheme() {
        DepTimeLayout()
    }
}

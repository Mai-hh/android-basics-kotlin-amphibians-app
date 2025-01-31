/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.amphibians.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amphibians.network.Amphibian
import com.example.amphibians.network.AmphibiansApi
import kotlinx.coroutines.launch
import java.lang.Exception

enum class AmphibianApiStatus {LOADING, ERROR, DONE}

class AmphibianViewModel : ViewModel() {

    // TODO: Create properties to represent MutableLiveData and LiveData for the API status
    private var _state = MutableLiveData<AmphibianApiStatus>()
    val state: LiveData<AmphibianApiStatus> = _state

    // TODO: Create properties to represent MutableLiveData and LiveData for a list of amphibian objects
    private var _amphibians = MutableLiveData<List<Amphibian>>()
    val amphibians: LiveData<List<Amphibian>> = _amphibians

    // TODO: Create properties to represent MutableLiveData and LiveData for a single amphibian object.
    private var _amphibian = MutableLiveData<Amphibian>()
    val amphibian: LiveData<Amphibian> = _amphibian
    //  This will be used to display the details of an amphibian when a list item is clicked

    // TODO: Create a function that gets a list of amphibians from the api service and sets the
    //  status via a Coroutine
    fun getAmphibianList() {
        viewModelScope.launch {
            _state.value = AmphibianApiStatus.LOADING
            try {
                _amphibians.value = AmphibiansApi.retrofitService.getAmphibians()
                _amphibians.value?.let {
                    _state.value = AmphibianApiStatus.DONE
                }
            } catch (e: Exception) {
                _state.value = AmphibianApiStatus.ERROR
                _amphibians.value = listOf()
            }
        }
    }

    fun onAmphibianClicked(amphibian: Amphibian) {
        _amphibian.value = amphibian
    }
}

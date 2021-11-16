package br.fiap.rm82371.hardware.ong.terraplanetaagua.utils.config

import android.app.Application
import androidx.annotation.Nullable
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.FirebaseDatasource
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.FirebaseSource
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.models.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.data.repositories.*
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.auth.AuthViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.home.HomeViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.oxigenio.OxigenioViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.ph.PHViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.profundidade.ProfundidadeViewModel
import br.fiap.rm82371.hardware.ong.terraplanetaagua.ui.temperatura.TemperaturaViewModel
import org.kodein.di.*
import org.kodein.di.android.x.androidXModule

class FirebaseApplication : Application(), DIAware {
    override val di: DI = DI.lazy {
        import(androidXModule(this@FirebaseApplication))
        // Fire Base auth
        bind() from singleton { FirebaseSource() }
        // Fire Base Ream Time Database
        bind() from singleton { FirebaseDatasource<PhSensorModel, Nullable>(Contanst.PH_REF) }
        bind() from singleton { FirebaseDatasource<ProfundidadeSensorModel, Nullable>(Contanst.PROFUNDIDADE_REF) }
        bind() from singleton { FirebaseDatasource<TemperaturaSensorModel, Nullable>(Contanst.TEMPERATURA_REF) }
        bind() from singleton { FirebaseDatasource<OxigenioSensorModel, Nullable>(Contanst.OXIGENIO_REF) }
        bind() from singleton { FirebaseDatasource<HomeModel, Nullable>(Contanst.HOME_REF) }
        // Repositories of Fire Base
        bind() from singleton { UserRepository(instance()) }
        bind() from singleton { PhSensorRepository(instance()) }
        bind() from singleton { ProfundidadeSensorRepository(instance()) }
        bind() from singleton { TemperaturaSensorRepository(instance()) }
        bind() from singleton { OxigenioSensorRepository(instance()) }
        bind() from singleton { HomeRepository(instance()) }
        // ViewModel Instanceszzzq
        bind() from provider { AuthViewModel.AuthViewModelFactory(instance()) }
        bind() from provider { HomeViewModel.HomeViewModelFactory(instance()) }
        bind() from provider { OxigenioViewModel.OxigenioViewModelFactory(instance()) }
        bind() from provider { PHViewModel.PHViewModelFactory(instance()) }
        bind() from provider { TemperaturaViewModel.TemperaturaViewModelFactory(instance()) }
        bind() from provider { ProfundidadeViewModel.ProfundidadeViewModelFactory(instance()) }
    }
}
package com.example.ocean_android_webservices

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: PokemonService = retrofit.create(PokemonService::class.java)

        val call = service.listarPokemons()

        val tvResultado = findViewById<TextView>(R.id.tvResultado)

        call.enqueue(object:Callback<PokemonApiResult> {
            override fun onResponse(
                call: Call<PokemonApiResult>,
                response: Response<PokemonApiResult>
            ) {
                // Checo se a chamada foi bem sucedida
                if (response.isSuccessful) {
                    // Obtenho o corpo da requisição
                    val pokemonApiResult = response.body()

                    // Valido se meu body é diferente de null
                    pokemonApiResult?.let {
                        // Recebo o resultado da api e exibo em um alerta
                        Toast.makeText(this@MainActivity, it.count.toString(), Toast.LENGTH_SHORT).show()

                        tvResultado.text = ""

                        it.results.forEach { pokemon ->
                            tvResultado.append(pokemon.name + "\n")
                        }
                    }
                }
            }

            override fun onFailure(call: Call<PokemonApiResult>, t: Throwable) {
                Toast.makeText(this@MainActivity, "Falha na requisição", Toast.LENGTH_SHORT).show()
            }

        })

        // Carregamento de imagem
        val ivPokemon = findViewById<ImageView>(R.id.ivPokemon)

        val imagemBulbasaur = "https://assets.pokemon.com/assets/cms2/img/pokedex/detail/001.png"
        Glide.with(this).load(imagemBulbasaur).into(ivPokemon)
    }
}
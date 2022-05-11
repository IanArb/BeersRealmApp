package com.example.beersrealmapp

import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.notifications.InitialResults
import io.realm.notifications.ResultsChange
import io.realm.notifications.UpdatedResults
import io.realm.query
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map

class BeersDatabase {

    private val configuration = RealmConfiguration.with(schema = setOf(BeerModel::class))
    private val realm = Realm.open(configuration)

    suspend fun saveBeer(beer: BeerData) {
        val beerDb = BeerModel().apply {
            id = beer.id
            name = beer.name
            brewery = beer.brewery
            imageUrl = beer.imageUrl
            abv = beer.abv
        }
        realm.write {
            this.copyToRealm(beerDb)
        }
    }

    fun findAllBeers(): Flow<List<BeerData>> {
        return realm.query<BeerModel>().asFlow()
            .map {
                when (it) {
                    is InitialResults -> {
                        transformToModel(it)
                    }
                    is UpdatedResults -> {
                        transformToModel(it)
                    }
                }
            }
    }

    private fun transformToModel(it: ResultsChange<BeerModel>) =
        it.list.map { beer ->
            BeerData(
                id = beer.id,
                name = beer.name,
                brewery = beer.brewery,
                imageUrl = beer.imageUrl,
                abv = beer.abv
            )
        }


    suspend fun removeBeerById(id: String) {
        realm.write {
            val beer = query<BeerModel>("id = $0", id)
            delete(beer)
        }
    }

    suspend fun removeAllBeers() {
        realm.write {
            val results = query<BeerModel>().find()
            delete(results)
        }
    }

}
package dev.marawanxmamdouh.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.marawanxmamdouh.asteroidradar.model.Asteroid

@Entity(tableName = "asteroid_table")
data class AsteroidEntity(
    @PrimaryKey
    val id: Long,
    val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)


/**
 * Map [AsteroidEntity] to [Asteroid]
 */
fun List<AsteroidEntity>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = it.closeApproachDate,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
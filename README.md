# Kotlin Stats Extractor

<!-- Plugin description -->
This is a plugin for IntelliJ IDEA that extracts statistics from `.kt` files using PSI and saves it in a `.csv` format.

Currently, it extracts statistics only for extension functions.

It is a very small project. Thus, can be easily extended.
<!-- Plugin description end -->

## How I Used It

I extracted statistics of source code from [Ktor](https://github.com/ktorio/ktor) and [Strikt](https://github.com/robfletcher/strikt).

### Visualization

I visualized the extracted stats in `notebooks/extensions.ipynb` --> [link](https://github.com/Furetur/kotlin-stats-extractor/blob/main/notebooks/extensions.ipynb).

## How to run?!

Run
```shell
./gradlew runIde -Pinput="absolute path to dataset" -Poutput="absolute path to output csv file"
```

In my case it was

```shell
./gradlew runIde -Pinput="/home/furetur/IdeaProjects/kotlin-stats-extractor/src/main/resources/datasets/ktor" -Poutput="/home/furetur/IdeaProjects/kotlin-stats-extractor/output/ktor.csv"
```
Run the tool several times if you want to extract data into different `.csv` files

## Extracted Stats

It only extracts stats for extension functions.

It only extract stats about extension function declarations and not about its usages.

### All the extracted stats

The full list of extracted stats can be found in `src/main/kotlin/statistic/Statistics.kt`

* Function name: split into words. Example: `T.findFile()` --> `find file`
* Line number
* File path
* Type params (generics). Example: `<T : Number> fun T.f()` --> `<T : Number>`
* Number of type params (generics)
* Does any of the type parameters have bounds?. Example: `true` for `<T : Number, S>`, `false` for `<T, S>`
* Function modifiers
* Number of params
* Does the function have block body. Example: `true` for `fun() {}`, `false` for `fun() = ...`
* Length of function body (in lines)
* Is top level
* Is inside class
* Is inside another fuction

### Why extension functions?
I consider extension functions to be one of the most important features of Kotlin because:

1. They are magical methods that appear when certain conditions are met.
   For instance, this function appears only if `Iterable<T>` is `Iterable<Int>`
   ```kotlin
   fun Iterable<Int>.sum(): Int {
        var sum = 0
        for (element in this) {
            sum += element
        }
        return sum
    }
    ```
   This allows developers to create better API's. See: [Strikt](https://strikt.io/)
2. They are a lot easier to discover than regular functions.
   When you press `.` the IDE finds all the extensions that can be used: `obj.extension()` 
3. They are a lot easier to read than regular functions. Regular functions are read right to left (`f(g(x))`)
   and extension functions are read left to right `x.g().f()`


## Output Examples

I extracted stats from [Ktor](https://github.com/ktorio/ktor) and [Strikt](https://github.com/robfletcher/strikt/)
and they are located at `output/ktor.csv` and `output/strikt.csv`

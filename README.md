# Wide&Deep serve example using BigDL, Mleap, and Play
A example on how to serve Wide&Deep model trained by BigDL and Spark using BigDL, Mleap, and Play

## Requirements
```scala
scalaVersion := "2.11.12"

val jacksonVersion = "2.6.7"
val sparkVersion = "2.3.1"
val analyticsZooVersion = "0.3.0"

libraryDependencies += guice
libraryDependencies += "com.intel.analytics.zoo" % "analytics-zoo-bigdl_0.7.1-spark_2.3.1" % analyticsZooVersion
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % jacksonVersion
libraryDependencies += "com.amazonaws" % "aws-java-sdk" % "1.11.354"
libraryDependencies += "ml.combust.mleap" %% "mleap-spark" % "0.12.0"
libraryDependencies += "ml.combust.mleap" %% "mleap-spark-extension" % "0.12.0"
libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)

dependencyOverrides += "com.fasterxml.jackson.core" % "jackson-databind" % jacksonVersion
dependencyOverrides += "com.google.guava" % "guava" % "17.0"
```

## How to run
```scala
sbt runProd
```

## API Introduction

### Check health
**Path:** /`healthcheck`  
**Methods:** GET  
**Params:**  
None  
**Return:**
```json
{"status": "ok"}
```

### Check model version
**Path:** /`versioncheck`  
**Methods:** GET  
**Params:**  
None  
**Return:**
```json
{
    "status": "ok",
    "wndModelVersion": "2018-12-01 13:54:21.000",
    "userIndexerModelVersion": "2018-12-13 18:10:22.000",
    "itemIndexerModelVersion": "2018-12-13 18:10:22.000"
}
```

### Run wide&deep model
**Path:** /`wndModel`  
**Methods:** POST  
**Params:**  
```json
{
    "COOKIE_ID": "80031540652853011880052",
    "ATC_SKU": ["677252", "440539", "429457"],
    "loyalty_ind": 1,
    "od_card_user_ind": 1,
    "hvb_flg": 1,
    "agent_smb_flg": 1,
    "interval_avg_day_cnt": 159.36,
    "customer_type_nm": "CONSUMER",
    "SKU_NUM": "244559",
    "rating": 2,
    "STAR_RATING_AVG": 1,
    "reviews_cnt": 1,
    "sales_flg": 1,
    "GENDER_CD": "F"
}
``` 
**Return:**
```json
[
    {
        "predict": 1,
        "probability": 0.7123838989001036,
        "atcSku": "677252"
    },
    {
        "predict": 1,
        "probability": 0.6807899545355797,
        "atcSku": "440539"
    },
    {
        "predict": 2,
        "probability": 0.6962240684141258,
        "atcSku": "429457"
    }
]
```
**Notes:** `predict` = 1 means not recommend, `predict` = 2 means recommend.  
**Error_message:** ""Nah nah nah nah nah...this request contains bad characters...""

## Contact & Feedback

 If you encounter bugs, feel free to submit an issue or pull request.
 Also you can mail to:
 * Luyang, Wang (tmacraft@hotmail.com)



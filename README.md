## Analysis Notebooks (PySpark & Scala)

Under `notebooks/` you’ll find the main analysis and modeling work:

- `cleaning_data.ipynb`  
  PySpark-based initial data cleaning and exploration.  
  Includes:
  - loading the raw CSV into Spark
  - handling empty / invalid values
  - basic correlations between key features (salary, savings, expenses, education, economic_class)
  - visualizations such as:
    - `income_percentiles_histogram.png`
    - `Avg_Years_Education_per_Income_Percentile_Group.png`

- `etl_scala.ipynb`  
  Scala Spark notebook focusing on ETL and first profiling steps:
  - column type checks
  - numeric ranges per column
  - missing-value statistics
  - grouped aggregations using the Scala DataFrame API  
  This shows the same logic implemented in Scala on top of the same Spark environment.

- `row-sector-ana.ipynb`  
  Analysis of the dataset by salary deciles and work sector:
  - splitting the population into income percentiles / deciles
  - aggregations per decile and sector (counts, averages)
  - examining how work sector distribution changes across income groups
  - providing business-oriented interpretation of these patterns.

- `UseCaseModle.ipynb`  
  Main modeling and use-case notebook.  
  Contains:
  - feature engineering for financial and behavioral variables:
    - `expense_ratio`, `disposable_income`, `salary_per_edu` and others
  - classification model for predicting `economic_class` (Random Forest with cross-validation)
  - evaluation metrics: Accuracy, Precision, Recall, F1, Confusion Matrix, per-class metrics, MAE on the ordered classes
  - K-Means clustering for population segmentation:
    - testing several values of `k`
    - selecting an “optimal” k using elbow-like behavior, pseudo R² and Silhouette score
    - interpreting clusters as different household profiles (e.g. low-income/high-expense vs. high-income/high-savings)
  - filling missing `economic_class` values using the trained model and re-running analysis on the completed dataset.

## 📊 Analysis Notebooks (PySpark, Scala & LLM Insights)

Under the `notebooks/` folder, you'll find the main analysis, modeling, and insight extraction notebooks:

---

### 🧹 `cleaning_data.ipynb`

PySpark-based initial data cleaning and exploration.

Includes:
- Loading the raw dataset into Spark
- Handling empty / invalid values
- Exploring key features: `salary`, `savings`, `expenses`, `education`, `economic_class`
- Basic visualizations:
  - `income_percentiles_histogram.png`
  - `Avg_Years_Education_per_Income_Percentile_Group.png`

---

### 🔁 `etl_scala.ipynb`

Scala Spark notebook focusing on ETL and profiling steps:

- Checking column types and value ranges  
- Gathering missing-value statistics  
- Grouped aggregations using the Scala DataFrame API  

Useful for comparing Scala vs. Python implementation of Spark logic.

---

### 📊 `row-sector-ana.ipynb`

In-depth analysis of the dataset by income deciles and work sectors:

- Splitting the population by income percentiles/deciles  
- Aggregating by decile and sector (counts, means, etc.)  
- Tracking how sector distribution changes across income groups  
- Providing interpretability for economic and employment patterns

---

### 🧠 `UseCaseModle.ipynb`

Primary notebook for modeling and use-case definition.

Includes:
- Feature engineering of behavioral and financial indicators:
  - `expense_ratio`, `disposable_income`, `salary_per_edu`, etc.
- Classification model (Random Forest) to predict `economic_class`
- Evaluation metrics:
  - Accuracy, Precision, Recall, F1, MAE, Confusion Matrix
- K-Means clustering to segment household profiles:
  - Tests multiple values of `k`
  - Selects optimal cluster count using pseudo R² and Silhouette score
- Fills missing `economic_class` values using model predictions

---

### 🤖 `UseCase_LLM_Insights.ipynb`

Experimental notebook leveraging Language Models (LLMs) to:

- Generate natural-language summaries from clustering/grouped outputs  
- Explain economic behaviors and cluster patterns automatically  
- Bridge structured data (Spark JSONs) with narrative insights  

**Workflow**: `Spark ➝ JSON ➝ LLM ➝ Business Insights`

---

## 📁 Data & Model Output Structure

---

### 🔹 `data/`

Contains the **raw structured dataset** used across notebooks:

- `datasetN3.txt`: Pre-cleaned dataset with anonymized user records  
- Features: salary, savings, education, expenses, etc.  
- Loaded by both PySpark and Scala pipelines

---

### 🔹 `output/json/`

Holds intermediate and final model outputs consumed by the LLM and analysis notebooks:

- `cluster_sizes.json`: Cluster counts from K-Means  
- `cluster_summary.json`: Summary statistics per cluster  
- `salary_decile_sector_summary.json`: Sector distribution per income decile  
- `sector_index_mapping.json`: Mapping between sector codes and full names  

These files support:
- Interpreting model results  
- Visualizing economic patterns  
- Generating text explanations using the LLM pipeline

---

🎯 **This structure helps separate raw data, modeling logic, and automated insights – enabling scalable and explainable analysis workflows.**

using System.Threading.Tasks;

namespace TemperatureAPI_Test.DataSourceService
{
    public class DataSource : IDataSource
    {
        private readonly string TempJsonOne = "{\"temperature\":\"25.6\",\"timestamp\":\"2019-04-14T12:00:00\"}";
        private readonly string TempJsonTwo = "{\"temperature\":\"28.2\",\"timestamp\":\"2019-04-14T12:00:30\"}";

        private string JsonStr;

        public void InitDataSource()
        {
            JsonStr = TempJsonOne;
        }

        public async Task DoWorkAsync()
        {
            if (JsonStr == TempJsonOne)
            {
                JsonStr = TempJsonTwo;
            }

            else if (JsonStr == TempJsonTwo)
            {
                JsonStr = TempJsonOne;
            }

            await Task.CompletedTask;
        }

        public string GetJson()
        {
            return JsonStr;
        }
    }
}
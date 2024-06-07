from modal import Image
from modal import App
from modal import web_endpoint
from pydantic import BaseModel
# from groq_modal import test_groq
import modal
import os

groq_image = (
	Image.debian_slim(python_version="3.10")
	.pip_install("groq")
)

app = App(name="groq_app", image=groq_image)

class Item(BaseModel):
	text: str


@app.function(secrets=[modal.Secret.from_name("groq-api-key")])
@web_endpoint(method="POST")
def groq_it_up(item: Item):
	from groq import Groq
	# test_groq(item.text)

	client = Groq(
		api_key=os.environ.get("GROQ_API_KEY")
	)

	SYSTEM_PROMPT = """
	You are a writing assistant. 
	Rewrite the user message in #social context and return in ONLY JSON format `{new_tone_message: <>}`. For example: "The run was amazing today! 🥰🥰 #runday #sunyy" 
	"""

	try: 
		completion = client.chat.completions.create(
			model="llama3-8b-8192",
			messages=[
				{
					"role": "system",
					"content": SYSTEM_PROMPT
				},
				{
					"role": "user",
					"content": item.text
				}
			],
			temperature=1,
			max_tokens=1024,
			top_p=1,
			stream=False,
			response_format={"type": "json_object"},
			stop=None,
		)

		return eval(completion.choices[0].message.content)

	except Exception as e:
		print("Error caught:")
		print(e)

		return {"error": str(e)}


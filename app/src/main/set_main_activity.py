import xml.etree.ElementTree as ET
from sys import argv

ANDROID_MANIFEST_PATH = "AndroidManifest.xml"  # the initial assumption is that the python script is within the same directory as the manifest
ANDROID = "{http://schemas.android.com/apk/res/android}"


class ActivityNotFound(Exception):
	pass


def check_main(activity):
	intent_filter = activity.findall('intent-filter')
	if not intent_filter:
		return False

	for intent in intent_filter:
		action = intent.find('action')
		category = intent.find('category')
		if action is None or category is None:
			continue

		if action.get(f"{ANDROID}name") == "android.intent.action.MAIN" and category.get(
				f"{ANDROID}name") == "android.intent.category.LAUNCHER":
			return intent

	return False


def set_main(activity):
	if check_main(activity):
		return

	element = ET.Element("intent-filter")
	element.append(ET.Element("action", {f"{ANDROID}name": "android.intent.action.MAIN"}))
	element.append(ET.Element("category", {f"{ANDROID}name": "android.intent.category.LAUNCHER"}))

	activity.append(element)


def remove_main(activity):
	if not check_main(activity):
		return

	activity.remove(check_main(activity))


def main():
	global ANDROID_MANIFEST_PATH

	if len(argv) >= 2:
		activity_name = argv[1]
	else:
		activity_name = input("Main Activity Name: ")

	if len(argv) >= 3:
		ANDROID_MANIFEST_PATH = argv[2]

	tree = ET.parse(ANDROID_MANIFEST_PATH)
	root = tree.getroot()

	application = root.find('application')

	activity = [i for i in application.findall('activity') if i.get(f"{ANDROID}name") == f".{activity_name}"]
	if len(activity) == 0:
		raise ActivityNotFound

	activities = application.findall('activity')
	for activity in activities:
		if activity.get(f"{ANDROID}name") == f".{activity_name}":
			set_main(activity)
		else:
			remove_main(activity)

	tree.write(ANDROID_MANIFEST_PATH)


if __name__ == '__main__':
	main()

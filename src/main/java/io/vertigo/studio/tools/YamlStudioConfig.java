package io.vertigo.studio.tools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class YamlStudioConfig {

	public List<YamlResourceConfig> resources = new ArrayList<>();
	public YamlMdaConfig mdaConfig = new YamlMdaConfig();

	public static class YamlMdaConfig {
		public String projectPackageName;
		public String targetGenDir;
		public String encoding;
		public Map<String, String> properties;
	}

	public static class YamlResourceConfig {
		public String type;
		public String path;
	}
}
